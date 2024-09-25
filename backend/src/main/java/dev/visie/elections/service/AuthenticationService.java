package dev.visie.elections.service;

import dev.visie.elections.dto.CreateUserDTO;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 UserService userService,
                                 PasswordEncoder passwordEncoder,
                                 ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    /**
     * Saves a new user.
     *
     * @param userDto the user data transfer object
     * @return the saved user
     */
    public ResponseEntity<?> register(CreateUserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);

        User existingUser = this.userService.getUserByEmail(user.getEmail()); // Check if user already exists

        if (existingUser != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
