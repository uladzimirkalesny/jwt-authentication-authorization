package com.github.uladzimirkalesny.jwt.controller;

import com.github.uladzimirkalesny.jwt.controller.request.AuthenticateUserRequest;
import com.github.uladzimirkalesny.jwt.controller.request.RegisterUserRequest;
import com.github.uladzimirkalesny.jwt.controller.response.AuthenticationResponse;
import com.github.uladzimirkalesny.jwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegisterUserRequest registerUserRequest) {
        return ResponseEntity.ok(authenticationService.registerUser(registerUserRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(
            @RequestBody AuthenticateUserRequest authenticateUserRequest) {
        return ResponseEntity.ok(authenticationService.authenticateUser(authenticateUserRequest));
    }

}
