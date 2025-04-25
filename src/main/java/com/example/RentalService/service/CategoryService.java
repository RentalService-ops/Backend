package com.example.RentalService.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.RentalService.DTO.CategoryDTO;
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Category;

public interface CategoryService {

    /**
     * Adds a new category to the system.
     * 
     * @param category The category object to be added.
     * @throws IllegalArgumentException if the user id specified in catgory details is null,
     * @throws UserNotFoundException  if user with specified id is not found.
     * @return The added Category object.
     */
    Category addCategory(Category category) throws IllegalArgumentException,UserNotFoundException;

    /**
     * Deletes a category by its ID.
     * 
     * @param id The ID of the category to be deleted.
     * @throws DataIntegrityViolationException if foreign key violation occur during category deletion.
     * @throws IllegalArgumentException if id is null.
     * @return A ResponseEntity containing a CategoryDTO if successful, or an error message.
     */
    ResponseEntity<?> deleteCategory(int id) throws DataIntegrityViolationException,IllegalArgumentException;
    
    /**
     * updates category based on provided details.
     * @throws IllegalArgumentException if id is null.
     * @param id id of the category to be updated.
     * @param body updated details of the category provided
     * @return Category object with updated details.
     * */
    Category updateCategoryById(int id, CategoryDTO body) throws IllegalArgumentException;

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
     * @throws IllegalArgumentException if id is null.
     */
    ResponseEntity<?> getCategoryByUserId(int id) throws IllegalArgumentException;
    
    /**
     * Retrieves all categories present in the database.
     * @param pageable pagination configuration
     * @param searchBy search parameter
     * @return Fetched category data from database.
     * */
	Page<Category> getAllCategories(Pageable pageable,String searchBy);
	
	/**
	 * Deletes category based on userId.
	 * @param userId the id of user whose category details are to be deleted.
	 * */
	void deleteCategoryByUserId(int userId);
	
}

