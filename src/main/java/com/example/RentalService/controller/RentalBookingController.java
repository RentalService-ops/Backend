package com.example.RentalService.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.RentalBookingsDTO;
import com.example.RentalService.service.RentalBookingService;

@RestController
@RequestMapping("/api/bookings")
public class RentalBookingController {
	
	@Autowired
	RentalBookingService service;
	
	@GetMapping("/getBookings")
	@PreAuthorize("hasRole('rental') or hasRole('admin')")
	public List<RentalBookingsDTO> getAllBookingsByRentalId(@RequestParam("id") int id){
		return service.findByRentalId(id).stream()
				.map(RentalBooking->new RentalBookingsDTO(RentalBooking))
				.collect(Collectors.toList());
	}
	
	@PatchMapping("/approve/{id}")
	@PreAuthorize("hasRole('rental')")
	public RentalBookingsDTO approveBooking(@PathVariable int id) {
		return new RentalBookingsDTO(service.approveBooking(id));
	}
	
	@PatchMapping("/reject/{id}")
	@PreAuthorize("hasRole('rental')")
	public RentalBookingsDTO rejectBooking(@PathVariable int id) {
		return new RentalBookingsDTO(service.rejectBooking(id));
	}
}
