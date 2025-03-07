package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
    List<Category> findByUserId(int userId);


}
