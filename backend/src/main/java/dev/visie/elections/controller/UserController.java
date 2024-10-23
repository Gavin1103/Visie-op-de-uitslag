package dev.visie.elections.controller;

import dev.visie.elections.config.PreAuthorizeAdmin;
import dev.visie.elections.dto.user.UpdateUserDTO;
import dev.visie.elections.model.User;
import dev.visie.elections.service.JwtService;
import dev.visie.elections.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PreAuthorizeAdmin()
    @GetMapping("/")
    @Operation(summary = "Get all users", description = "Get all users from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorizeAdmin()
    @PutMapping("/{id}")
    @Operation(summary = "Edit a user", description = "Edit a user in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User edited successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user details"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<?> editUser(@RequestBody UpdateUserDTO userDto, @PathVariable Long id) {
        User existingUser = userService.getUserById(id); // Check if user already exists
        if (existingUser == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        }

        return userService.editUser(userDto, existingUser, true);
    }

    @PutMapping("/self")
    @Operation(summary = "Edit the current user", description = "Edit the current user in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User edited successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user details"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<?> editSelf(@RequestBody UpdateUserDTO userDto, HttpServletRequest request) {
        String id = this.jwtService.extractUserData(request, "sub");
        User existingUser = userService.getUserByEmail(id); // Check if user already exists
        if (existingUser == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        }

        return userService.editUser(userDto, existingUser, false);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get user by email", description = "Get a user by email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User users = userService.getUserByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorizeAdmin
    @DeleteMapping("/{email}")
    @Operation(summary = "Delete user by email", description = "Delete a user by email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    public ResponseEntity<User> deleteUserByEmail(@PathVariable String email) {
        User users = userService.deleteUserByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
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

    }