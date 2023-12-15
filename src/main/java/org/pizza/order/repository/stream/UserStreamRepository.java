package org.pizza.order.repository.stream;

import org.pizza.order.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserStreamRepository {
    private List<User> userList = new ArrayList<>();
    private Long lastUsedId = 0L;

    private Long generateId(){
        ++lastUsedId;
        return lastUsedId;
    }

    public User save(final User user){
        user.setId(generateId());
        userList.add(user);
        return user;
    }

    public List<User> findAll(){
        return userList;
    }

    public Optional<User> findById(final Long id) {
        return userList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public void deleteById(final Long id){
        userList = userList.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
    }
}
