package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Address;

/**
 * Repository interface for Address entity.
 * Provides basic CRUD operations and custom query methods.
 */
public interface addressRepo extends JpaRepository<Address, Integer> {

    /**
     * Finds a list of addresses associated with a specific user ID.
     *
     * @param userId the ID of the user
     * @return list of addresses for the given user
     */
    List<Address> findByUserId(int userId);
}
