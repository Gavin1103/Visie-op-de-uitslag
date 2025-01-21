package dev.visie.elections.seeder;

import dev.visie.elections.dto.user.CreateUserDTO;
import dev.visie.elections.service.AuthenticationService;
import dev.visie.elections.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
@Profile({"local", "dev", "prod", "gavin"})
public class UserSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(UserSeeder.class);

    private final JsonSeeder jsonSeeder;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public UserSeeder(JsonSeeder jsonSeeder, AuthenticationService authenticationService, UserService userService) {
        this.jsonSeeder = jsonSeeder;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        try {
            List<CreateUserDTO> users = jsonSeeder.loadData("users.json", CreateUserDTO.class);

            // Validate if the list is loaded properly
            if (users == null || users.isEmpty()) {
                return;
            }

            for (CreateUserDTO userDto : users) {
                if (userService.existsByEmail(userDto.getEmail())) {
                    continue;
                }
                try {
                    authenticationService.register(userDto, userDto.getRoleName(), true);
                } catch (Exception e) {
                    logger.error("Error creating user {}: {}", userDto.getEmail(), e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to load users data: {}", e.getMessage());
        }
    }

}
