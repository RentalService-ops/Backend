package com.example.RentalService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Equipment;

public interface EquipmentRepo extends JpaRepository<Equipment, Integer> {

}
