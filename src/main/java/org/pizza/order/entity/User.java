package org.pizza.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private BigDecimal money;
    @Column
    private Boolean isAuthorized = Boolean.FALSE;
    @Column
    private Boolean isActive = Boolean.TRUE;

    @OneToMany(mappedBy = "user")
    private List<PizzaOrder> basket = new ArrayList<>();

}
