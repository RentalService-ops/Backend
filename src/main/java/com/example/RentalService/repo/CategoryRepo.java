package com.example.RentalService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.RentalService.model.Category;


public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
    /**
     * Finds a list of categories associated with a specific user ID.
     * Wrapped in Optional to indicate the result may be absent.
     *
     * @param userId the ID of the user
     * @return an Optional containing the list of categories if found
     */
    Optional<List<Category>> findByUserId(int userId);
    
    /**
	 * Deletes category based on userId.
	 * @param userId the id of user whose category details are to be deleted.
	 * */
    @Modifying  //Annotation to add when using update , delete queries.
    void deleteCategoryByUserId(int userId);
    
    /**
     * Searches for category by name (case-insensitive) and returns results in a paginated format.
     *
     * @param name the partial or full name of the category
     * @param pageable the pagination information
     * @return a page of category matching the search term
     */
    Page<Category> findByNameContainingIgnoreCase(String name,Pageable pageable);
}
