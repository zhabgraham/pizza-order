package org.pizza.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.pizza.order.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByFirstNameAndLastName(String firstName, String lastName);
    List<User> findAllByIsActiveTrue();
}
