package com.example.RentalService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.RentalService.model.Category;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private UserService userService;
	
//	public Category addCategory(Category category) {
//		System.out.println("in");
////		Users user = userRepositry.findById(category.getUser().getId());
//		System.out.println(category.toString());
//
//		Users user = userService.findUsreById(category.getUser().getId());
//		System.out.println(user);
//		if(user != null) {
//			category.setUser(user);
//		}
//		return repo.save(category);
//	}
	
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
//		System.out.println(category);
		if(category!=null) {
			repo.deleteById(id);
			return new ResponseEntity<>(category,HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
	}
	
	public List<Category> getAllcategory(){
		return repo.findAll();
	}
	
	public List<Category> getCategoryByUserId(int id){
	    return repo.findByUserId(id);

	}
	
}
