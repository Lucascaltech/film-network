package com.luca.film.auth;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService service;


    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    /**
     * Register a new user in the system
     * @param request the registration request
     * @return a response entity with status CREATED
     * @throws MessagingException if an error occurs while sending the email
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    /**
     * Authenticate the user account with the token
     * @param request the authentication request
     * @return the authentication response
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticateRequest request

    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confirmAccount(@RequestParam("token") String token) throws MessagingException {
        service.confirmAccount(token);
    }


}

