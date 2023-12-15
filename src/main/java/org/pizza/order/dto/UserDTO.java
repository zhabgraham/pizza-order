package org.pizza.order.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pizza.order.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;

    private Boolean isAuthorized = Boolean.FALSE;

    private List<PizzaOrderDTO> basket = new ArrayList<>();

    private BigDecimal money = BigDecimal.valueOf(1000);

    public static UserDTO toDTO(final User user) {
        final UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setIsAuthorized(user.getIsAuthorized());

        List<PizzaOrderDTO> pizzaOrderDTOs = user.getBasket().stream()
                .map(PizzaOrderDTO::toDTO)
                .collect(Collectors.toList());

        userDTO.setBasket(pizzaOrderDTOs);
        userDTO.setMoney(user.getMoney());

        return userDTO;
    }
}
