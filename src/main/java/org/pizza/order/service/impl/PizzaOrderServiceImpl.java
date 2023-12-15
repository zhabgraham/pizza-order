package org.pizza.order.service.impl;

import lombok.AllArgsConstructor;
import org.pizza.order.dto.PizzaOrderDTO;
import org.pizza.order.dto.UserDTO;
import org.pizza.order.entity.PizzaOrder;
import org.pizza.order.entity.User;
import org.pizza.order.exceptions.BadRequestException;
import org.pizza.order.repository.PizzaOrderRepository;
import org.pizza.order.service.PizzaOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PizzaOrderServiceImpl implements PizzaOrderService {
    private PizzaOrderRepository pizzaOrderRepository;
    private UserServiceImpl userRepository;

    @Override
    public List<PizzaOrderDTO> findAll() {
        return pizzaOrderRepository.findAll().stream()
                .map(PizzaOrderDTO::toDTO)
                .collect(Collectors.toList());

    }

    private boolean isPizzaOrderInCustomerBasket(User user, PizzaOrder pizzaOrder) {
        return user.getBasket().contains(pizzaOrder);
    }

    @Override
    public void orderPizza(Long userId, String pizzaType) {
        UserDTO userDTO = userRepository.findDTOById(userId);

        if (userDTO == null || pizzaType == null) {
            throw new BadRequestException("Invalid customer or pizza type");
        }

        PizzaOrderDTO pizzaOrderDTO = new PizzaOrderDTO();
        pizzaOrderDTO.setPizzaType(pizzaType);
        pizzaOrderDTO.setConfirmed(false);

        pizzaOrderDTO.setToppings(new ArrayList<>());

        userDTO.getBasket().add(pizzaOrderDTO);
        userRepository.save(userDTO);
    }


    @Override
    public PizzaOrderDTO findDTOById(final Long id) {
        final PizzaOrder pizzaOrder = findById(id);
        return PizzaOrderDTO.toDTO(pizzaOrder);
    }

    private PizzaOrder findById(Long id) {
        return pizzaOrderRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("PizzaOrder with id {%s} not found", id)));
    }

    @Override
    public PizzaOrderDTO save(final PizzaOrderDTO pizzaOrderDTO) {
        final PizzaOrder pizzaOrder = new PizzaOrder();

        pizzaOrder.setPizzaType(pizzaOrderDTO.getPizzaType());
        pizzaOrder.setConfirmed(pizzaOrderDTO.getConfirmed());

        pizzaOrderRepository.save(pizzaOrder);

        return PizzaOrderDTO.toDTO(pizzaOrder);
    }

    @Override
    public void deleteFromBasket(Long orderId) {
        pizzaOrderRepository.deleteById(orderId);
    }
    @Override
    public void deleteAllFromBasket(Long userId) {
        User user = userRepository.findById(userId);
        user.getBasket().clear();

        userRepository.save(UserDTO.toDTO(user));
    }
}
