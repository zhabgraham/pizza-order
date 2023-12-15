package org.pizza.order.service.impl;

import lombok.AllArgsConstructor;
import org.pizza.order.dto.ToppingDTO;
import org.pizza.order.entity.PizzaOrder;
import org.pizza.order.entity.Topping;
import org.pizza.order.exceptions.BadRequestException;
import org.pizza.order.repository.PizzaOrderRepository;
import org.pizza.order.repository.ToppingRepository;
import org.pizza.order.service.ToppingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToppingServiceImpl implements ToppingService {
    private final ToppingRepository toppingRepository;
    private final PizzaOrderRepository pizzaOrderRepository;
    private final UserServiceImpl userService;

    @Override
    public ToppingDTO findDTOById(final Long id) {
        final Topping topping = findById(id);

        return ToppingDTO.toDTO(topping);
    }
    @Override
    public Topping findById(final Long id) {
        return toppingRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Topping with id {%s} not found", id)));
    }
    @Override
    public List<ToppingDTO> findAll() {
        return toppingRepository.findAll().stream()
                .map(ToppingDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addTopping(Long orderId, Long toppingId) {
        PizzaOrder pizzaOrder = pizzaOrderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("Order not found"));

        Topping topping = toppingRepository.findById(toppingId)
                .orElseThrow(() -> new BadRequestException("Topping not found"));

        pizzaOrder.getToppings().add(topping);
        pizzaOrderRepository.save(pizzaOrder);
    }

    @Override
    public ToppingDTO save(final ToppingDTO toppingDTO) {
        Topping topping = new Topping();

        topping.setName(toppingDTO.getName());
        topping.setPrice(toppingDTO.getPrice());

        if (toppingDTO.getId() != null) {
            topping.setId(toppingDTO.getId());
        }

        Topping savedTopping = toppingRepository.save(topping);

        return ToppingDTO.toDTO(savedTopping);
    }
    @Override
    public void deleteById(Long orderId, Long toppingId) {
        PizzaOrder pizzaOrder = pizzaOrderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("Order not found"));

        Topping topping = toppingRepository.findById(toppingId)
                .orElseThrow(() -> new BadRequestException("Topping not found"));

        pizzaOrder.getToppings().remove(topping);
        pizzaOrderRepository.save(pizzaOrder);
    }
}
