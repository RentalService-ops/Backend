package com.example.RentalService.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.RentalService.model.Users;

import jakarta.servlet.http.HttpServletResponse;


public interface AuthService {

    /**
     * Registers a new user by saving their information to the repository.
     *
     * @param user The user object containing registration details such as email, password, and role.
     * @return The saved user object with an encrypted password.
     */
    Users register(Users user);

    /**
     * Retrieves all the users from the repository.
     *
     * @return A list of all users in the repository.
     */
    List<Users> getAllusers();

    /**
     * Finds a user by their unique ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The user object corresponding to the provided ID.
     */
    Users findUsreById(int id);

    /**
     * Verifies the login credentials of a user and generates a JWT token if successful.
     *
     * @param user The user object containing the login credentials (email and password).
     * @param response The HTTP response object used to set the JWT token in the cookies.
     * @return A ResponseEntity containing the JWT token and a success message if authentication is successful, 
     *         or an error message with status 401 if the credentials are invalid.
     */
    ResponseEntity<?> verify(Users user, HttpServletResponse response);
}
