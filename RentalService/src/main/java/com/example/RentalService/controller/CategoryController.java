package com.example.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.model.Category;
import com.example.RentalService.service.CategoryService;

@RestController
@RequestMapping("/api/rental/")
@PreAuthorize("hasAuthority('ROLE_rental')")
@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@PostMapping("/addCategory")
	public Category addCategory(@RequestBody Category category) {
		return service.addCategory(category);
	}
	
	 @GetMapping("/check-role")
	    public String checkRole() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        return "User: " + authentication.getName() + ", Roles: " + authentication.getAuthorities();
	    }
	
	@DeleteMapping("/category/{id}")
	public String deleteCategoryById(@PathVariable int id){
		System.out.println("in");
		return service.deleteCategory(id);
	}
}
