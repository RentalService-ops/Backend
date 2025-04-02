package com.example.RentalService.serviceImpl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.RentalService.DTO.CategoryDTO;
import com.example.RentalService.model.Category;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.CategoryRepo;
import com.example.RentalService.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private AuthServiceImpl userService;
		
	public Category addCategory(Category category) {

	    if (category.getUser() == null || category.getUser().getId() == 0) {
	        throw new RuntimeException("User ID is required but was null or 0.");
	    }

	    // Fetch the user from DB
	    Users user = userService.findUsreById(category.getUser().getId());

	    if (user == null) {
	        throw new RuntimeException("User with ID " + category.getUser().getId() + " not found.");
	    }

	    category.setUser(user); // Assign the fetched user
	    return repo.save(category);
	}

	
	public ResponseEntity<?> deleteCategory(int id) {
		
		Category category = repo.findById(id).get();
		if(category!=null) {
			repo.deleteById(id);
			return new ResponseEntity<>(new CategoryDTO(category),HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<?> getAllcategory(){
		return ResponseEntity.ok(
				repo.findAll()
				.stream()
				.map(category->new CategoryDTO(category))
				.collect(Collectors.toList())
				);
	}
	
	public ResponseEntity<?> getCategoryByUserId(int id){
		return ResponseEntity.ok(
				repo.findByUserId(id)
				.stream()
				.map(category->new CategoryDTO(category))
				.collect(Collectors.toList())
				);
	}
	
}
