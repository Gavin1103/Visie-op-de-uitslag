package dev.visie.elections.service;

import dev.visie.elections.dto.user.UpdateUserDTO;
import dev.visie.elections.dto.user.UserProfileResponse;
import dev.visie.elections.model.Role;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.ConfirmationTokenRepository;
import dev.visie.elections.repository.RoleRepository;
import dev.visie.elections.repository.TokenRepository;
import dev.visie.elections.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final ConfirmationTokenRepository confirmationToken;
    private final RoleRepository roleRepository;


    @Autowired
    public UserService(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository, ConfirmationTokenRepository confirmationToken, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.confirmationToken = confirmationToken;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Edits a user.
     *
     * @param userDto     the user data transfer object
     * @param changeRoles whether to change the user's roles
     * @return the edited user
     */
    public ResponseEntity<?> editUser(UpdateUserDTO userDto, User existingUser, boolean changeRoles) {
        User user = modelMapper.map(userDto, User.class);

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }

        if (changeRoles) {
            Role role = new Role();
            role.setName(userDto.getRoleName());
            role.setUser(user);
            user.setRoles(Set.of(role));
        } else {
            user.setRoles(existingUser.getRoles());
        }

        user.setEnabled(userDto.isEnabled());
        user.setId(existingUser.getId());
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
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

    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    /**
     * Deletes a user by email.
     *
     * @param email the email address
     * @return the deleted user
     */
    public User deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return deleteUser(user);
    }

    public User deleteUserById(long id) {
        User user = userRepository.getUserById(id);
        return deleteUser(user);
    }

    private User deleteUser(User user) {
        if (user != null) {
            tokenRepository.deleteByUser_Id(user.getId());
            confirmationToken.deleteByUser_Id(user.getId());
        }

        userRepository.delete(user);
        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public UserProfileResponse getUserByToken(String token) {

        String userEmail = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            throw new RuntimeException("User not found for the given token");
        }
        return modelMapper.map(user, UserProfileResponse.class);
    }

}