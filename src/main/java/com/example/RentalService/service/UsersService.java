package com.example.RentalService.service;

import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Users;

public interface UsersService {

    /**
     * Retrieves a user by their user ID.
     * 
     * @param id the ID of the user to retrieve
     * @return the user object with the given user ID
     * @throws UserNotFoundException if user with specified id is not found.
     */
    Users getUserByUserId(int id) throws UserNotFoundException;

    /**
     * Saves a new or existing user to the database.
     * 
     * @param user the user object to save
     * @return the saved user object
     */
    Users saveUser(Users user);
}
