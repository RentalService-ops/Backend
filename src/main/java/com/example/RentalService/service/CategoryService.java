package com.example.RentalService.service;

import org.springframework.http.ResponseEntity;

import com.example.RentalService.model.Category;

public interface CategoryService {

    /**
     * Adds a new category to the system.
     * 
     * @param category The category object to be added.
     * @return The added Category object.
     */
    Category addCategory(Category category);

    /**
     * Deletes a category by its ID.
     * 
     * @param id The ID of the category to be deleted.
     * @return A ResponseEntity containing a CategoryDTO if successful, or an error message.
     */
    ResponseEntity<?> deleteCategory(int id);

    /**
     * Retrieves all categories from the system.
     * 
     * @return A ResponseEntity containing a list of CategoryDTO objects.
     */
    ResponseEntity<?> getAllcategory();

    /**
     * Retrieves categories associated with a specific user by their ID.
     * 
     * @param id The ID of the user whose categories are to be fetched.
     * @return A ResponseEntity containing a list of CategoryDTO objects.
     */
    ResponseEntity<?> getCategoryByUserId(int id);
}

