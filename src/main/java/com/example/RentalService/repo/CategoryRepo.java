package com.example.RentalService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Category;

/**
 * Repository interface for Category entity.
 * Provides CRUD operations and custom query methods.
 */
public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
    /**
     * Finds a list of categories associated with a specific user ID.
     * Wrapped in Optional to indicate the result may be absent.
     *
     * @param userId the ID of the user
     * @return an Optional containing the list of categories if found
     */
    Optional<List<Category>> findByUserId(int userId);

}
