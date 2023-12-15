package org.pizza.order.service;


import org.pizza.order.dto.UserDTO;

import java.util.*;

public interface UserService {
    UserDTO findDTOById(Long id);
    List<UserDTO> findAll();
    void deleteById(Long id);
    UserDTO save(UserDTO user);
    UserDTO update(UserDTO user);
    UserDTO searchByNameAndSurname(String firstName, String lastName);

}
