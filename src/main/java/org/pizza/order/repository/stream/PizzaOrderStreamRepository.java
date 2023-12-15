package org.pizza.order.repository.stream;

import org.pizza.order.dto.PizzaOrderDTO;
import org.pizza.order.dto.UserDTO;
import org.pizza.order.entity.PizzaOrder;
import org.pizza.order.exceptions.BadRequestException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PizzaOrderStreamRepository  {
    private List<PizzaOrder> pizzaOrders = new ArrayList<>();
    private Long lastUsedId = 0L;

    private Long generateId(){
        ++lastUsedId;
        return lastUsedId;
    }

    public PizzaOrder saveToBasket(final PizzaOrder pizzaOrder) {
        pizzaOrder.setId(generateId());
        pizzaOrders.add(pizzaOrder);
        return pizzaOrder;
    }

    public void editPizzaOrder(UserDTO userDTO, PizzaOrderDTO pizzaOrderDTO, String pizzaTypeAdded) {
        userDTO.getBasket().stream()
                .filter(e -> e.getPizzaType().equals(pizzaOrderDTO.getPizzaType()))
                .findFirst()
                .ifPresentOrElse(
                        e -> {
                            e.setPizzaType(pizzaTypeAdded);
                        },
                        () -> {
                            throw new BadRequestException(String.format("PizzaOrder with id {%s} not found", pizzaOrderDTO.getPizzaType()));
                        }
                );
    }

    public Optional<PizzaOrder> findById(final Long id){
        return pizzaOrders.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public void deleteById(final Long id) {
        pizzaOrders = pizzaOrders.stream()
                .filter(e -> !e.getId().equals(id))
                .collect(Collectors.toList());
    }

}
