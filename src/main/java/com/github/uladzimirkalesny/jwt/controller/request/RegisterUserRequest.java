package com.github.uladzimirkalesny.jwt.controller.request;

/**
 * Register user request
 */
public record RegisterUserRequest(String firstName, String lastName, String email, String password) {
}
