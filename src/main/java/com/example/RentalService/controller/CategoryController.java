package com.example.RentalService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.model.Category;
import com.example.RentalService.model.Users;
import com.example.RentalService.service.AuthService;
import com.example.RentalService.service.CategoryService;


//Controller for handling Category operations.
@RestController
@RequestMapping("/api/rental/")
public class CategoryController {
	
	
	//Service for interacting with Category Entity.
	@Autowired
	private CategoryService service;
	
	//Service for interacting with User Entity.
	@Autowired
	private AuthService userService;
	
	/**
	 * Adds a new Category to the database
	 * @Param category: Category to be added.
	 * Returns the added category.
	 * */
	 @PostMapping("/addCategory")
	    public ResponseEntity<?> addCategory(@RequestBody Category category) {

	        if (category.getUser() == null || category.getUser().getId() <= 0) {
	            return ResponseEntity.badRequest().body("Valid User ID is required");
	        }

	        Users user = userService.findUsreById(category.getUser().getId());
	        if (user == null) {
	            return ResponseEntity.badRequest().body("User not found");
	        }

	        category.setUser(user);
	        Category savedCategory = service.addCategory(category);
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	    }

	/**
	 * Deletes the category with corresponding id.
	 * @Param id: The id of the category to be deleted.
	 * Returns deleted category.
	 * */
	@DeleteMapping("/category/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable int id){
		System.out.println("in");
		return service.deleteCategory(id);
	}
	
	/**
	 * Returns all the categories registered.
	 * */
	@GetMapping("/getAllCategory")
	public ResponseEntity<List<Category>> getAllCategory(){
		return new ResponseEntity<>(service.getAllcategory(), HttpStatus.OK);
	}
	
	/**
	 * Returns categories corresponding to specific user id.
	 * @Param id: id of the user whose category details are to be fetched.
	 * */
	@GetMapping("/category/{id}")
	public List<Category> getCategoryByUser(@PathVariable int id){
		return service.getCategoryByUserId(id);
	}
}
