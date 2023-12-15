package org.pizza.order.service;

import org.pizza.order.dto.ToppingDTO;
import org.pizza.order.entity.Topping;

import java.util.List;

public interface ToppingService {
    ToppingDTO findDTOById(Long id);

    Topping findById(Long id);

    List<ToppingDTO> findAll();

    void addTopping(Long orderId, Long toppingId);

    ToppingDTO save(ToppingDTO toppingDTO);

    void deleteById(Long orderId, Long toppingId);
}
