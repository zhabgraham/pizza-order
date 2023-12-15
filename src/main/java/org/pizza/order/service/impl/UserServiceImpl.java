package org.pizza.order.service.impl;

import lombok.AllArgsConstructor;
import org.pizza.order.dto.PizzaOrderDTO;
import org.pizza.order.entity.PizzaOrder;
import org.pizza.order.entity.Topping;
import org.pizza.order.service.UserService;
import org.pizza.order.entity.User;
import org.pizza.order.repository.UserRepository;
import org.pizza.order.dto.UserDTO;
import org.pizza.order.exceptions.BadRequestException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public UserDTO findDTOById(final Long id) {
        final User user = findById(id);

        return UserDTO.toDTO(user);
    }

    public User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("User with id {%s} not found", id)));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAllByIsActiveTrue().stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO save(final UserDTO userDTO) {
        final User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMoney(userDTO.getMoney());

        List<PizzaOrder> tempBasket = new ArrayList<>();
        for (PizzaOrderDTO orderDTO : userDTO.getBasket()) {
            PizzaOrder order = new PizzaOrder();
            order.setId(orderDTO.getId());
            order.setPizzaType(orderDTO.getPizzaType());
            order.setPrice(orderDTO.getPrice());
            order.setConfirmed(orderDTO.getConfirmed());
            order.setUser(user);

            List<Topping> toppings = orderDTO.getToppings().stream()
                    .map(toppingDTO -> {
                        Topping topping = new Topping();
                        topping.setId(toppingDTO.getId());
                        topping.setName(toppingDTO.getName());
                        topping.setPrice(toppingDTO.getPrice());
                        return topping;
                    })
                    .collect(Collectors.toList());
            order.setToppings(toppings);

            tempBasket.add(order);
        }

        user.setBasket(tempBasket);
        userRepository.save(user);

        return UserDTO.toDTO(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        user.setIsActive(Boolean.FALSE);
        userRepository.save(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        if (userDTO.getId() == null) {
            throw new BadRequestException("Id can't be null");
        }

        final User savedUser = findById(userDTO.getId());
        savedUser.setFirstName(userDTO.getFirstName());
        savedUser.setLastName(userDTO.getLastName());
        userRepository.save(savedUser);

        return UserDTO.toDTO(savedUser);
    }

    @Override
    public UserDTO searchByNameAndSurname(String firstName, String lastName) {
        return userRepository.findOneByFirstNameAndLastName(firstName, lastName)
                .map(UserDTO::toDTO)
                .orElseThrow(() -> new BadRequestException(String.format("User with name {%s} and surname {%s} not found",
                        firstName, lastName)));
    }
}
