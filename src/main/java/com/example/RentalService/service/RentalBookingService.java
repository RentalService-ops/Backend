package com.example.RentalService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RentalService.model.BookingStatus;
import com.example.RentalService.model.Equipment;
import com.example.RentalService.model.Rental_Bookings;
import com.example.RentalService.repo.EquipmentRepo;
import com.example.RentalService.repo.RentalBookingRepository;

@Service
public class RentalBookingService {
	
	@Autowired
	RentalBookingRepository rentalRepo;
	
	@Autowired
	EquipmentRepo equipmentRepo;
	
	public List<Rental_Bookings> findByUserId(int id){
		return rentalRepo.findByUser_Id(id);
	}
	
	public List<Rental_Bookings> findByRentalId(int id){
		return rentalRepo.findByRenter_Id(id);
	}
	
	public Rental_Bookings rejectBooking(int id) {
		Rental_Bookings booking=rentalRepo.findById(id).get();
		booking.setStatus(BookingStatus.REJECTED);
		Equipment equipment=equipmentRepo.findById(booking.getEquipment().getEquipmentId()).get();
		equipment.setQuantity(equipment.getQuantity()-booking.getEquipment_quantity());
		equipmentRepo.save(equipment);
		rentalRepo.save(booking);
		return booking;
	}
	
	public Rental_Bookings approveBooking(int id) {
		Rental_Bookings booking=rentalRepo.findById(id).get();
		booking.setStatus(BookingStatus.APPROVED);
		rentalRepo.save(booking);
		return booking;
	}
}
