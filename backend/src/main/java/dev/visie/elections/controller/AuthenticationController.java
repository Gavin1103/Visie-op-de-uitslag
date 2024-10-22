package dev.visie.elections.controller;

import dev.visie.elections.config.PreAuthorizeAdmin;
import dev.visie.elections.dto.user.CreateUserDTO;
import dev.visie.elections.dto.JwtRequest;
import dev.visie.elections.model.enums.RoleEnum;
import dev.visie.elections.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @Operation(summary = "Authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }

    @PostMapping(value = "/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDTO user) throws Exception {
        return ResponseEntity.ok(authenticationService.register(user, RoleEnum.USER, false));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh the JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully refreshed token"),
            @ApiResponse(responseCode = "401", description = "Invalid token")
    })
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return this.authenticationService.refreshToken(request);
    }

    @GetMapping("/confirm-account/{token}")
    @Operation(summary = "Confirm a user's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully confirmed account"),
            @ApiResponse(responseCode = "400", description = "Invalid token")
    })
    public ResponseEntity<?> confirmAccount(@PathVariable String token) {
        return authenticationService.confirmUserAccount(token);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Process a password reset request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully processed request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> processForgotPassword(@RequestParam String email) {
        return authenticationService.forgotPassword(email);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset a user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully reset password"),
            @ApiResponse(responseCode = "400", description = "Invalid token")
    })
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String password) {
        return authenticationService.resetPassword(token, password);
    }


    @PreAuthorizeAdmin
    @GetMapping("/is-admin")
    @Operation(summary = "Check if the current user is an admin", description = "Returns true if the current user has admin privileges.")
    public boolean isAdmin(){
        return true;
    }
}