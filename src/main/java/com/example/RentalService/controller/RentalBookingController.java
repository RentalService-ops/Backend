package com.example.RentalService.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.RentalBookingsDTO;
import com.example.RentalService.model.Rental_Bookings;
import com.example.RentalService.service.RentalBookingService;

@RestController
@RequestMapping("/api/bookings")
public class RentalBookingController {
	
	@Autowired
	RentalBookingService service;
	
	
	 /**
     * Book equipment.
     */
    @PreAuthorize("hasAnyRole('ROLE_user')")
    @PostMapping("/equipmentBooking")
    public ResponseEntity<?> bookEquipment(@RequestBody Rental_Bookings booking) {
        System.out.println(booking.toString());
        return service.equipmentBooking(booking);
    }
    
    /**
     * Get booking details by user ID.
     */
    @PreAuthorize("hasAnyRole('ROLE_user')")
    @GetMapping("/bookingDetails/{userId}")
    public ResponseEntity<?> getBookingDetailsByUserId(@PathVariable int userId) {
        return service.getBookingDetailsByUserId(userId);
    }

    /**
     * Cancel a booking by ID.
     */
    @PreAuthorize("hasAnyRole('ROLE_user')")
    @PutMapping("/cancelBooking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId) {
        return service.cancelBooking(bookingId);
    }
    
	
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
