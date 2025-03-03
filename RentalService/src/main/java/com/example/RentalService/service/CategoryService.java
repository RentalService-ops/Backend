package com.example.RentalService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RentalService.model.Category;
import com.example.RentalService.repo.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo repo;
	
	public Category addCategory(Category category) {
		return repo.save(category);
	}
	
	public String deleteCategory(int id) {
		
		Category category = repo.findById(id).get();
		System.out.println(category);
		if(category!=null) {
			repo.deleteById(id);
			return "Success";
		}
		
		return "Not Success";
	}
}
