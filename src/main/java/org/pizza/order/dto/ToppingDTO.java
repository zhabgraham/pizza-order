package org.pizza.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pizza.order.entity.Topping;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToppingDTO {
    private Long id;
    private String name;
    private BigDecimal price;

    public static ToppingDTO toDTO(final Topping topping) {
        final ToppingDTO toppingDTO = new ToppingDTO();

        toppingDTO.setId(topping.getId());
        toppingDTO.setName(topping.getName());
        toppingDTO.setPrice(topping.getPrice());

        return toppingDTO;
    }

}
