package dev.visie.elections.service;

import dev.visie.elections.dto.user.UserDTO;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper; // Inject ModelMapper


    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves a user by email.
     *
     * @param email the email address
     * @return a user with the specified email address
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByToken(String token) {
        String userEmail = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found for the given token");
        }
        return user;
    }
}
