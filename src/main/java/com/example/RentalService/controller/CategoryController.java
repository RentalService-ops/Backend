package com.example.RentalService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.model.Category;
import com.example.RentalService.model.Users;
import com.example.RentalService.service.CategoryService;
import com.example.RentalService.service.UserService;

@RestController
@RequestMapping("/api/rental/")
//@PreAuthorize("hasAuthority('ROLE_rental')")
//@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@Autowired
	private UserService userService;
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


//	
	
//	@PostMapping("/addCategory")
//	public ResponseEntity<?> addCategory(@RequestBody Map<String, Object> body) {
//	    System.out.println("Received Request: " + body);
//
//	    int userId = (int) body.get("user_id");
//	    String name = (String) body.get("name");
//	    String description = (String) body.get("description");
//
//	    Users user = userService.findUsreById(userId);
//	    
//	    if (user == null) {
//	        System.out.println("User not found with ID: " + userId);
//	        return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
//	    }
//
//	    Category category = new Category(user, name, description);
//	    Category savedCategory = service.addCategory(category);
//
//	    return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
//	}

	

	
	 @GetMapping("/check-role")
	    public String checkRole() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        return "User: " + authentication.getName() + ", Roles: " + authentication.getAuthorities();
	    }
	
	@DeleteMapping("/category/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable int id){
		System.out.println("in");
		return service.deleteCategory(id);
	}
	
//	@GetMapping("/getAllCategory")
//	public  List<Category> getAllCategory(){
//		return service.getAllcategory();
//	}
	
	@GetMapping("/getAllCategory")
	public ResponseEntity<List<Category>> getAllCategory(){
		return new ResponseEntity<>(service.getAllcategory(), HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}")
	public List<Category> getCategoryByUser(@PathVariable int id){
		return service.getCategoryByUserId(id);
	}
}
