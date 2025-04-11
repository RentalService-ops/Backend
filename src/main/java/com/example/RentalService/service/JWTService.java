package com.example.RentalService.service;

import org.springframework.security.core.userdetails.UserDetails;


public interface JWTService {

    /**
     * Generates a JWT token with the provided user details.
     *
     * @param userId The user ID to include in the claims.
     * @param username The username (subject) to include in the token.
     * @param role The role of the user to include in the claims.
     * @return The generated JWT token as a string.
     * @throws IllegalArgumentException If the input parameters are invalid
     */
    String generateToken(String userId, String username, String role) throws IllegalArgumentException;
    
    /**
     * Generates a JWT token with the provided user details.
     *
     * @param useremail The email of the user to include in the token.
     * @return The generated JWT token as a string.
     * @throws IllegalArgumentException If the input parameters are invalid
     */
    String generateToken(String useremail) throws IllegalArgumentException;

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token from which the username will be extracted.
     * @return The username extracted from the token.
     */
    String extractUserName(String token);

    /**
     * Validates the JWT token against the provided user details.
     *
     * @param token The JWT token to be validated.
     * @param userDetails The user details to check the validity of the token.
     * @return True if the token is valid, otherwise false.
     * @throws IllegalArgumentException If the token is invalid.
     */
    boolean validateToken(String token, UserDetails userDetails) throws IllegalArgumentException;
}
