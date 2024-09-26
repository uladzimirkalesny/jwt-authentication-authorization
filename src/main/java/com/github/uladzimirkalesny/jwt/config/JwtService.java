package com.github.uladzimirkalesny.jwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT service for extracting user email from JWT token
 */
@Component
public class JwtService {

    private static final String SECRET_KEY = "a248af3700ba5e76fec2c4c5f8072044cdd23612b8b79799254ee8724069020b";

    /**
     * Extract user email from JWT token
     *
     * @param jwtToken JWT token
     * @return user email
     */
    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Extract claim from JWT token
     *
     * @param jwtToken       JWT token
     * @param claimsResolver claims resolver
     * @param <T>            claim type
     * @return claim
     */
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Generate JWT token
     *
     * @param userDetails user details
     * @return JWT token
     */
    public String generateJwtToken(UserDetails userDetails) {
        return generateJwtToken(Map.of(), userDetails);
    }

    /**
     * Generate JWT token
     *
     * @param extraClaims extra claims
     * @param userDetails user details
     * @return JWT token
     */
    public String generateJwtToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims()
                .add(extraClaims)
                .and()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validate JWT token
     *
     * @param jwtToken    JWT token
     * @param userDetails user details
     * @return true if JWT token is valid, false otherwise
     */
    public boolean isJwtTokenValid(String jwtToken, UserDetails userDetails) {
        String userEmail = extractUserEmail(jwtToken);
        return userDetails.getUsername().equals(userEmail) && !isJwtTokenExpired(jwtToken);
    }

    /**
     * Check if JWT token is expired
     *
     * @param jwtToken JWT token
     * @return true if JWT token is expired, false otherwise
     */
    private boolean isJwtTokenExpired(String jwtToken) {
        return extractJwtTokenExpiration(jwtToken).before(new Date());
    }

    /**
     * Extract JWT token expiration
     *
     * @param jwtToken JWT token
     * @return JWT token expiration
     */
    private Date extractJwtTokenExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * Extract all claims from JWT token
     *
     * @param jwtToken JWT token
     * @return all claims
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    /**
     * Get signing key
     *
     * @return signing key
     */
    private SecretKey getSigningKey() {
        byte[] secretKeyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

}
