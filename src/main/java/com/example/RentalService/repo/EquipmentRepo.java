package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Equipment;

/**
 * Repository interface for Equipment entity.
 * Provides CRUD operations and custom query methods related to equipment listings.
 */
public interface EquipmentRepo extends JpaRepository<Equipment, Integer> {

    /**
     * Retrieves a list of equipment owned by a specific user.
     *
     * @param userId the ID of the user
     * @return list of equipment belonging to the user
     */
    List<Equipment> findByUserId(int userId);

    /**
     * Retrieves a specific equipment by its unique equipment ID.
     *
     * @param equipmentId the ID of the equipment
     * @return the equipment entity
     */
    Equipment findByEquipmentId(int equipmentId);

    /**
     * Retrieves a list of equipment under a specific category ID.
     *
     * @param categoryId the ID of the category
     * @return list of equipment under the given category
     */
    List<Equipment> findByCategory_CategoryId(int categoryId);

    /**
     * Searches for equipment by name (case-insensitive) and returns results in a paginated format.
     *
     * @param name the partial or full name of the equipment
     * @param pageable the pagination information
     * @return a page of equipment matching the search term
     */
    Page<Equipment> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
