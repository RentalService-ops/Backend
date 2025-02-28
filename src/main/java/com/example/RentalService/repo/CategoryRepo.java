package com.example.RentalService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
