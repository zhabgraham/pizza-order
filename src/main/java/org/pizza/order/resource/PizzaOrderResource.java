package org.pizza.order.resource;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.pizza.order.dto.PizzaOrderDTO;
import org.pizza.order.service.PizzaOrderService;

@RestController
@RequestMapping("/api/order-pizza")
@AllArgsConstructor
public class PizzaOrderResource {
    @Autowired
    private PizzaOrderService pizzaOrderService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> orderPizza(@PathVariable Long userId) {
        pizzaOrderService.orderPizza(userId, "Hawaian");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}")
    public void deleteFromBasket(@PathVariable Long orderId) {
        pizzaOrderService.deleteFromBasket(orderId);
    }

    @DeleteMapping("/{customerId}/delete-all")
    public void deleteAllFromBasket(@PathVariable Long customerId) {
        pizzaOrderService.deleteAllFromBasket(customerId);
    }

    @GetMapping("/{orderId}")
    public PizzaOrderDTO findDTOById(@PathVariable Long orderId) {
        return pizzaOrderService.findDTOById(orderId);
    }
}
