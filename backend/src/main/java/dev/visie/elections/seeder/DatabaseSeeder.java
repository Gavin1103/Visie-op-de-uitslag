package dev.visie.elections.seeder;

import dev.visie.elections.dto.user.CreateUserDTO;
import dev.visie.elections.model.enums.RoleEnum;
import dev.visie.elections.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DatabaseSeeder implements CommandLineRunner {

    private final AuthenticationService authenticationService;

    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        CreateUserDTO userDto = new CreateUserDTO(
                "User",
                "user@user.com",
                "tester",
                RoleEnum.USER
        );

        this.authenticationService.register(userDto, userDto.getRoleName(), true);

        CreateUserDTO adminDto = new CreateUserDTO(
                "Admin",
                "admin@admin.com",
                "admin",
                RoleEnum.ADMIN
        );
        this.authenticationService.register(adminDto, adminDto.getRoleName(), true);
    }
}