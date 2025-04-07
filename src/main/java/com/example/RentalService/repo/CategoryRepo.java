package com.example.RentalService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
    Optional<List<Category>> findByUserId(int userId);


}
