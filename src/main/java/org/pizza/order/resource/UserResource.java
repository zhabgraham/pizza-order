package org.pizza.order.resource;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.pizza.order.dto.UserDTO;
import org.pizza.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity createCustomer(final @RequestBody @Valid UserDTO userDTO){
        final UserDTO responseBody = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable @Min(1) Long id) {
        UserDTO userDTO = userService.findDTOById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/all-users")
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @PutMapping
    public UserDTO updateUser(final @RequestBody @Valid UserDTO userDTO, final @PathVariable Long id){
        return userService.update(userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(final @PathVariable Long id){
        userService.deleteById(id);
    }

    @GetMapping("/search")
    public UserDTO find(final @RequestParam String firstName,final @RequestParam String lastName){
        return userService.searchByNameAndSurname(firstName, lastName);
    }
}