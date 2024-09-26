package com.github.uladzimirkalesny.jwt.service;

import com.github.uladzimirkalesny.jwt.config.JwtService;
import com.github.uladzimirkalesny.jwt.controller.request.AuthenticateUserRequest;
import com.github.uladzimirkalesny.jwt.controller.request.RegisterUserRequest;
import com.github.uladzimirkalesny.jwt.controller.response.AuthenticationResponse;
import com.github.uladzimirkalesny.jwt.user.Role;
import com.github.uladzimirkalesny.jwt.user.User;
import com.github.uladzimirkalesny.jwt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register user. Save user to database and generate jwt token. Return authentication response.
     *
     * @param registerUserRequest register user request
     * @return authentication response
     */
    public AuthenticationResponse registerUser(RegisterUserRequest registerUserRequest) {
        User user = User.builder()
                .firstName(registerUserRequest.firstName())
                .lastName(registerUserRequest.lastName())
                .email(registerUserRequest.email())
                .password(passwordEncoder.encode(registerUserRequest.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateJwtToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    /**
     * Authenticate user. Check user credentials and generate jwt token. Return authentication response.
     *
     * @param authenticateUserRequest authenticate user request
     * @return authentication response
     */
    public AuthenticationResponse authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticateUserRequest.email(),
                authenticateUserRequest.password()
        );

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = userRepository.findByEmail(authenticateUserRequest.email()).orElseThrow();

        String jwtToken = jwtService.generateJwtToken(user);

        return new AuthenticationResponse(jwtToken);
    }
}
