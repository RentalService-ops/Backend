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
}
