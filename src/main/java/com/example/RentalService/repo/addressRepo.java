package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Address;

public interface addressRepo extends JpaRepository<Address, Integer> {

    List<Address> findByUserId(int userId);

}
