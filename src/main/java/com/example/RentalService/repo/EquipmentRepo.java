package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RentalService.model.Equipment;

public interface EquipmentRepo extends JpaRepository<Equipment, Integer> {
	   List<Equipment> findByUserId(int var1);

	   Equipment findByEquipmentId(int var1);
}
