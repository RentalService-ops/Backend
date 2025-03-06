package com.example.RentalService.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Users;

public interface UserRepositry extends JpaRepository<Users, Integer> {

	 Optional<Users> findByEmail(String email);
   
//	  Users findByUserId(int userId);
}
