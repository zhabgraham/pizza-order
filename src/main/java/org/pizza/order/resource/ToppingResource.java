package org.pizza.order.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.pizza.order.dto.ToppingDTO;
import org.pizza.order.service.ToppingService;
import java.util.List;

@RestController
@RequestMapping("/api/toppings")
public class ToppingResource {
    private final ToppingService toppingService;

    public ToppingResource(ToppingService toppingService) {
        this.toppingService = toppingService;
    }

    @GetMapping
    public ResponseEntity<List<ToppingDTO>> getAllToppings() {
        List<ToppingDTO> toppings = toppingService.findAll();
        return ResponseEntity.ok(toppings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToppingDTO> getToppingById(@PathVariable Long id) {
        ToppingDTO toppingDTO = toppingService.findDTOById(id);
        return ResponseEntity.ok(toppingDTO);
    }

    @PostMapping
    public ResponseEntity<ToppingDTO> createTopping(@RequestBody ToppingDTO toppingDTO) {
        ToppingDTO createdTopping = toppingService.save(toppingDTO);
        return ResponseEntity.ok(createdTopping);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToppingDTO> updateTopping(@PathVariable Long id, @RequestBody ToppingDTO toppingDTO) {
        toppingDTO.setId(id);
        ToppingDTO updatedTopping = toppingService.save(toppingDTO);
        return ResponseEntity.ok(updatedTopping);
    }

    @DeleteMapping("/order-pizza/{orderId}/topping/{toppingId}")
    public ResponseEntity<Void> deleteToppingFromOrder(@PathVariable Long orderId, @PathVariable Long toppingId) {
        toppingService.deleteById(orderId, toppingId);
        return ResponseEntity.ok().build();
    }

}
