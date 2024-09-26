package com.github.uladzimirkalesny.jwt.controller.request;

/**
 * Authenticate user request
 */
public record AuthenticateUserRequest(String email, String password) {
}
