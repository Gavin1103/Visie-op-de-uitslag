package dev.visie.elections.service;

import dev.visie.elections.dto.AuthenticationResponse;
import dev.visie.elections.dto.user.CreateUserDTO;
import dev.visie.elections.dto.ForgotPasswordResponse;
import dev.visie.elections.dto.JwtRequest;
import dev.visie.elections.model.ConfirmationToken;
import dev.visie.elections.model.Role;
import dev.visie.elections.model.Token;
import dev.visie.elections.model.User;
import dev.visie.elections.model.enums.RoleEnum;
import dev.visie.elections.model.enums.TokenType;
import dev.visie.elections.repository.ConfirmationTokenRepository;
import dev.visie.elections.repository.TokenRepository;
import dev.visie.elections.repository.UserRepository;
import dev.visie.elections.service.models.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    @Value("${cors.origin}")
    private String corsOrigin;

    @Autowired
    public AuthenticationService(UserService userService,
                                 @Lazy JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 TokenRepository tokenRepository,
                                 ModelMapper modelMapper,
                                 UserRepository userRepository,
                                 ConfirmationTokenRepository confirmationTokenRepository,
                                 EmailService emailService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
    }


    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param authenticationRequest the authentication request containing the user's email and password
     * @return a ResponseEntity containing a JwtResponse with the JWT token if authentication is successful,
     * or an error message with an appropriate HTTP status code if authentication fails
     */
    public ResponseEntity<?> authenticate(JwtRequest authenticationRequest) {

        final User user = userService.getUserByEmail(authenticationRequest.getEmail());

        if (user == null) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        if(!user.isEnabled()){
            return new ResponseEntity<>("Account not confirmed", HttpStatus.FORBIDDEN);
        }

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", user.getAuthoritiesList());

        final String token = jwtService.generateToken(extraClaims, user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);

        return new ResponseEntity<>(AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build(), HttpStatus.OK);
    }

    /**
     * Saves a new user.
     *
     * @param userDto the user data transfer object
     * @return the saved user
     */
    public ResponseEntity<?> register(CreateUserDTO userDto, RoleEnum roleEnum, boolean isEnabled) {
        User user = modelMapper.map(userDto, User.class);

        User existingUser = this.userService.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(isEnabled);

        Role userRole = new Role();
        userRole.setName(roleEnum);
        userRole.setUser(user);
        user.setRoles(new HashSet<>(Set.of(userRole)));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        User savedUser = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        ConfirmationToken confirmationToken = new ConfirmationToken(savedUser);
        confirmationTokenRepository.save(confirmationToken);

        String confirmationLink = corsOrigin + "/confirm/" + confirmationToken.getConfirmationToken();
        String emailContent = "<div style=\"font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd;\">"
                + "<h2 style=\"color: #4CAF50;\">Hello " + user.getUsername() + ",</h2>"
                + "<p style=\"font-size: 16px; line-height: 1.5;\">Thank you for registering! Please click the link below to confirm your email address:</p>"
                + "<p style=\"text-align: center; margin: 20px 0;\">"
                + "<a href=\"" + confirmationLink + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background-color: #4CAF50; text-decoration: none; border-radius: 5px;\">Confirm your account</a>"
                + "</p>"
                + "<p style=\"font-size: 14px; line-height: 1.5; color: #666;\">If you did not register, please ignore this email.</p>"
                + "<p style=\"font-size: 14px; line-height: 1.5;\">Best regards,<br><span style=\"font-weight: bold;\">Elections</span></p>"
                + "</div>";

        emailService.sendHtmlEmail(user.getEmail(), "Email Confirmation", emailContent);

        return new ResponseEntity<>(AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .confirmationToken(confirmationToken.getConfirmationToken())
                .build(), HttpStatus.CREATED);
    }


    /**
     * Saves the user's token.
     *
     * @param user     the user
     * @param jwtToken the JWT token
     */
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Revokes all tokens for a user.
     *
     * @param user the user
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Refreshes the JWT token.
     *
     * @param request the HTTP request
     * @return
     * @throws IOException if an I/O error occurs
     */
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            return null;
        }

        var user = this.userService.getUserByEmail(userEmail);

        if (user == null) {
            throw new BadCredentialsException("Invalid credentials");
        }

        if (jwtService.isTokenValid(refreshToken, user)) {
            var accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);

            return new ResponseEntity<>(AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build(), HttpStatus.OK);
        }
        return null;
    }

    /**
     * Confirms the user's account.
     *
     * @param confirmationToken the confirmation token
     * @return a ResponseEntity containing a success message if the account is confirmed, or an error message with an appropriate HTTP status code if the account is not confirmed
     */
    public ResponseEntity<?> confirmUserAccount(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userService.getUserByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return new ResponseEntity<>("Account confirmed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The link is invalid or broken!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Processes the forgot password request.
     *
     * @param email the email address
     * @return a ResponseEntity containing the confirmation token if the user exists, or an error message with an appropriate HTTP status code if the user does not exist
     */
    public ResponseEntity<?> forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            return new ResponseEntity<>(ForgotPasswordResponse.builder()
                    .username(user.getEmail())
                    .confirmationToken(confirmationToken.getConfirmationToken())
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Resets the user's password.
     *
     * @param token       the token
     * @param newPassword the new password
     * @return a response entity, containing a success message if the password is reset, or an error message with an appropriate HTTP status code if the password is not reset
     */
    public ResponseEntity<String> resetPassword(String token, String newPassword) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        if (confirmationToken != null) {
            User user = userRepository.findByEmail(confirmationToken.getUser().getEmail());
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Token not found", HttpStatus.NOT_FOUND);
    }
}