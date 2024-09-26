package dev.visie.elections.controller;

import dev.visie.elections.config.PreAuthorizeAdmin;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    @Operation(summary = "Test endpoint")
    public String test() {
        return "Test";
    }

    @PreAuthorizeAdmin
    @GetMapping("/admin")
    @Operation(summary = "Admin test endpoint")
    public String admin() {
        return "Test admin";
    }
}