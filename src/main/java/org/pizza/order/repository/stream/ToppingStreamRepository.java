package org.pizza.order.repository.stream;

import org.pizza.order.entity.Topping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ToppingStreamRepository {
    private List<Topping> toppingList = new ArrayList<>();
    private Long lastUsedId = 0L;

    private Long generateId(){
        ++lastUsedId;
        return lastUsedId;
    }

    public Topping save(final Topping topping){
        topping.setId(generateId());
        toppingList.add(topping);
        return topping;
    }

    public List<Topping> findAll(){
        return toppingList;
    }

    public Optional<Topping> findById(final Long id) {
        return toppingList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public void deleteById(final Long id){
        toppingList = toppingList.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
    }
}
