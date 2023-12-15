package org.pizza.order.service;

import org.pizza.order.dto.PizzaOrderDTO;
import java.util.List;

public interface PizzaOrderService {
    PizzaOrderDTO findDTOById(final Long id);
    List<PizzaOrderDTO> findAll();
    PizzaOrderDTO save(final PizzaOrderDTO pizzaOrderDTO);
    void deleteFromBasket(Long orderId);
    void deleteAllFromBasket(Long customerId);
    void orderPizza(Long userId, String pizzaType);
}
