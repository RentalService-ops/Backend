package com.example.RentalService.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.RentalService.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByEmail(String email);
	 
	@Query("SELECT u FROM Users u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Users> searchByUsername(@Param("search") String search, Pageable pageable);
}
