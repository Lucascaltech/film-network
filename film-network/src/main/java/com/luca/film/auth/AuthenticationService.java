package com.luca.film.auth;

import com.luca.film.email.EmailService;
import com.luca.film.email.EmailTemplateName;
import com.luca.film.role.Role;
import com.luca.film.role.RoleRepository;
import com.luca.film.security.JwtService;
import com.luca.film.user.Token;
import com.luca.film.user.TokenRepository;
import com.luca.film.user.User;
import com.luca.film.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.beans.Transient;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    /**
     * Register a new user and send an activation email
     * @param request the registration request
     * @throws MessagingException if an error occurs while sending the email
     */
    public void register(RegistrationRequest request) throws MessagingException {
        Role userRole = roleRepository.findByName("USER")
                /* todo - better exception handling */
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
        User user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }


    /**
     * Generate a token for the given user details
     * @param user the user
     * @return the generated token
     */
    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    /**
     * Send an activation email to the user with the activation token
     * @param user the user
     * @throws MessagingException if an error occurs while sending the email
     */
    private void sendValidationEmail(User user ) throws MessagingException {
        String newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Activate your account"
        );


    }

    /**
     * Generate a random activation code of the given length
     * @param length the length of the code
     * @return the generated code
     */
    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }


    /**
     * Authenticate a user with the given credentials
     * @param request the authentication request
     * @return the authentication response
     */
    public AuthenticationResponse authenticate(@Valid AuthenticateRequest request) {

        Authentication auth  =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        Map<String, Object> claims = new HashMap<>();
        User user = (User) auth.getPrincipal();
        claims.put("fullName", user.getFullName());
        String jwt = jwtService.generateToken(claims,user);

        return AuthenticationResponse.builder().token(jwt).build();
    }


    /**
     * Confirm the account with the given token
     * @param token the token
     */
    @Transient
    public void confirmAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new IllegalStateException("Activation token has expired. A new token has been sent.");
        }

        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        user.setEnabled(true);
        user.setAccountLocked(false);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
