package dev.visie.elections.controller;

import dev.visie.elections.config.PreAuthorizeAdmin;
import dev.visie.elections.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private final EmailService emailService;

    @Autowired
    public TestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/")
    @Operation(summary = "Test endpoint")
    public String test() {
        emailService.sendEmail("aaron.laan@gmail.com", "Test", "Test");
        return "Test";
    }

    @PreAuthorizeAdmin
    @GetMapping("/admin")
    @Operation(summary = "Admin test endpoint")
    public String admin() {
        return "Test admin";
    }
}