package org.pizza.order.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.pizza.order.entity.PizzaOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaOrderDTO {
    private long id;
    @NotNull
    @NotBlank
    private String pizzaType = "Hawaian";

    private Boolean confirmed = false;

    @NotNull
    private BigDecimal price;

    @NotNull
    private List<ToppingDTO> toppings;
    public static PizzaOrderDTO toDTO(final PizzaOrder pizzaOrder) {
        final PizzaOrderDTO pizzaOrderDTO = new PizzaOrderDTO();

        pizzaOrderDTO.setPizzaType(pizzaOrder.getPizzaType());
        pizzaOrderDTO.setConfirmed(pizzaOrder.isConfirmed());
        pizzaOrderDTO.setPrice(pizzaOrder.getPrice());

        if (pizzaOrder.getToppings() != null) {
            List<ToppingDTO> toppingDTOs = pizzaOrder.getToppings().stream()
                    .map(ToppingDTO::toDTO)
                    .collect(Collectors.toList());
            pizzaOrderDTO.setToppings(toppingDTOs);
        }


        return pizzaOrderDTO;
    }
}
