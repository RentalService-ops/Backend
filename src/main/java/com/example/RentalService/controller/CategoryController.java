package com.example.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.CategoryDTO;
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Category;
import com.example.RentalService.model.Users;
import com.example.RentalService.service.AuthService;
import com.example.RentalService.service.CategoryService;


//Controller for handling Category operations.
@RestController
@RequestMapping("/api/category/")
public class CategoryController {
	
	
	//Service for interacting with Category Entity.
	@Autowired
	private CategoryService service;
	
	//Service for interacting with User Entity.
	@Autowired
	private AuthService userService;
	
	/**
	 * Adds a new Category to the database
	 * @param category: Category to be added.
     * @throws IllegalArgumentException if the user id specified in catgory details is null,
     * @throws UserNotFoundException if user with specified id is not found.
	 * Returns the added category.
	 * */
	 @PostMapping("/addCategory")
	 @PreAuthorize("hasRole('rental')")
	 public ResponseEntity<?> addCategory(@RequestBody Category category) throws IllegalArgumentException,UserNotFoundException{

	        if (category.getUser() == null || category.getUser().getId() <= 0) {
	            return ResponseEntity.badRequest().body("Valid User ID is required");
	        }

	        Users user = userService.findUsreById(category.getUser().getId());
	        if (user == null) {
	            return ResponseEntity.badRequest().body("User not found");
	        }

	        category.setUser(user);
	        Category savedCategory = service.addCategory(category);
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryDTO(savedCategory));
	    }

	/**
	 * Deletes the category with corresponding id.
	 * @param id: The id of the category to be deleted.
	 * @throws DataIntegrityViolationException if foreign key constraint is violated while deleting category.
	 * Returns deleted category.
	 * */
	@DeleteMapping("/category/{id}")
	@PreAuthorize("hasRole('rental')")
	public ResponseEntity<?> deleteCategoryById(@PathVariable int id) throws DataIntegrityViolationException{
		return service.deleteCategory(id);
	}
	
	
    /**
     * updates category based on provided details.
     * @param id id of the category to be updated.
     * @param body updated details of the category provided
     * @return ResponseEntity object with updated category details.
     * */
	@PutMapping("/category/{id}")
	@PreAuthorize("hasRole('rental')")
	public ResponseEntity<?> updateCategoryById(@PathVariable int id,@RequestBody CategoryDTO body){
		return ResponseEntity.status(HttpStatus.OK).body(new CategoryDTO(service.updateCategoryById(id,body)));
	}
	
	/**
	 * Returns all the categories registered.
	 * */
	@GetMapping("/getAllCategory")
	@PreAuthorize("hasRole('user') or hasRole('admin') or hasRole('rental')")
	public ResponseEntity<?> getAllCategory(){
		return new ResponseEntity<>(service.getAllcategory(), HttpStatus.OK);
	}
	
	/**
	 * Returns categories corresponding to specific user id.
	 * @param id: id of the user whose category details are to be fetched.
     *@throws IllegalArgumentException if id is null.
	 * */
	@GetMapping("/category/{userId}")
	@PreAuthorize("hasRole('rental')")
	public ResponseEntity<?> getCategoryByUser(@PathVariable int userId) throws IllegalArgumentException{
		return ResponseEntity.ok(service.getCategoryByUserId(userId));
	}
}
