package com.example.RentalService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Users;

public interface UsersService {

    /**
     * Retrieves a user by their user ID.
     * 
     * @param id the ID of the user to retrieve
     * @return the user object with the given user ID
     * @throws UserNotFoundException if the user with the specified ID is not found
     */
    Users getUserByUserId(int id) throws UserNotFoundException;

    /**
     * Saves a new or existing user to the database.
     * 
     * @param user the user object to save
     * @return the saved user object
     */
    Users saveUser(Users user);

    /**
     * Retrieves a paginated list of all users.
     * 
     * @param pageable the pagination and sorting information
     * @return a paginated list of users
     */
    Page<Users> getAllUsers(Pageable pageable);

    /**
     * Retrieves a paginated list of users based on a search query.
     * 
     * @param search the keyword to search users by (e.g., name or email)
     * @param pageable the pagination and sorting information
     * @return a paginated list of users matching the search criteria
     */
    Page<Users> getAllUsers(String search, Pageable pageable);

    /**
     * Deletes a user by their ID.
     * 
     * @param id the ID of the user to delete
     */
    void deleteUser(int id);

    /**
     * Updates an existing user's details.
     * 
     * @param id the ID of the user to update
     * @param updatedUser the user object containing updated details
     * @return the updated user object
     */
    Users updateUser(int id, Users updatedUser);
}
