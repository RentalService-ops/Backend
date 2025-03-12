package com.example.RentalService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RentalService.model.Rental_Bookings;
import com.example.RentalService.repo.RentalBookingRepository;

@Service
public class RentalBookingService {
	
	@Autowired
	RentalBookingRepository rentalRepo;
	
	public List<Rental_Bookings> findByUserId(int id){
		return rentalRepo.findByUser_Id(id);
	}
	
	public List<Rental_Bookings> findByRenterId(int id){
		return rentalRepo.findByRenter_Id(id);
	}
}
