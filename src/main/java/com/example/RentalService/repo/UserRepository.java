package com.example.RentalService.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.RentalService.model.Users;


public interface UserRepository extends JpaRepository<Users, Integer> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, otherwise empty
     */
    Optional<Users> findByEmail(String email);

    /**
     * Searches for users whose usernames match a given search term (case-insensitive).
     *
     * @param search the search term for the username
     * @param pageable pagination configuration
     * @return a Page of users whose usernames contain the search term
     */
    @Query("SELECT u FROM Users u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Users> searchByUsername(@Param("search") String search, Pageable pageable);
}
