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
	
	public Category addCategory(Category category) {
		
//		Users user = userRepositry.findById(category.getUser().getId());
//		
		Users user = userService.findUsreById(category.getUser().getId());
		
		if(user != null) {
			category.setUser(user);
		}
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
}
