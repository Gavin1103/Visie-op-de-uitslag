package dev.visie.elections.controller;

import dev.visie.elections.model.User;
import dev.visie.elections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to get a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to get a user by their token.
     *
     * @param token the token of the user
     * @return the user associated with the token, or 404 if not found
     */
    @GetMapping("/token/{token}")
    public ResponseEntity<User> getUserByToken(@PathVariable String token) {
        User user = userService.getUserByToken(token);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
