package com.github.uladzimirkalesny.jwt.config;

import org.springframework.stereotype.Component;

/**
 * JWT service for extracting user email from JWT token
 */
@Component
public class JwtService {

    /**
     * Extract user email from JWT token
     *
     * @param jwtToken JWT token
     * @return user email
     */
    public String extractUserEmail(String jwtToken) {
        return null;
    }

}
