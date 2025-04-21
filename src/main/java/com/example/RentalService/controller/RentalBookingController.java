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

/**
 * Controller for handling equipment rental bookings.
 */
@RestController
@RequestMapping("/api/bookings")
public class RentalBookingController {
    
    @Autowired
    RentalBookingService service;

    /**
     * Books equipment for the authenticated user.
     *
     * @param booking the rental booking details
     * @return ResponseEntity with the booking confirmation or error message
     */
    @PreAuthorize("hasAnyRole('ROLE_user')")
    @PostMapping("/equipmentBooking")
    public ResponseEntity<?> bookEquipment(@RequestBody Rental_Bookings booking) {
        return service.equipmentBooking(booking);
    }

    /**
     * Retrieves all booking details for a specific user.
     *
     * @param userId the ID of the user
     * @return ResponseEntity containing a list of bookings for the user
     */
    @PreAuthorize("hasAnyRole('ROLE_user')")
    @GetMapping("/bookingDetails/{userId}")
    public ResponseEntity<?> getBookingDetailsByUserId(@PathVariable int userId) {
        return service.getBookingDetailsByUserId(userId);
    }

    /**
     * Cancels a booking by its ID if allowed.
     *
     * @param bookingId the ID of the booking to cancel
     * @return ResponseEntity with the status of the cancellation
     */
    @PreAuthorize("hasAnyRole('ROLE_user')")
    @PutMapping("/cancelBooking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId) {
        return service.cancelBooking(bookingId);
    }

    /**
     * Retrieves all bookings by rental ID (for owners).
     *
     * @param id the rental user's ID
     * @return list of rental bookings for the given rental ID
     */
    @GetMapping("/getBookings")
    @PreAuthorize("hasRole('rental') or hasRole('admin')")
    public ResponseEntity<List<RentalBookingsDTO>> getAllBookingsByRentalId(@RequestParam("id") int id) {
        return ResponseEntity.ok(service.findByRentalId(id).stream()
                .map(RentalBooking -> new RentalBookingsDTO(RentalBooking))
                .collect(Collectors.toList()));
    }

    /**
     * Approves a booking by its ID.
     *
     * @param id the ID of the booking to approve
     * @return approved booking wrapped in RentalBookingsDTO
     */
    @PatchMapping("/approve/{id}")
    @PreAuthorize("hasRole('rental')")
    public ResponseEntity<RentalBookingsDTO> approveBooking(@PathVariable int id) {
        return ResponseEntity.ok(new RentalBookingsDTO(service.approveBooking(id)));
    }

    /**
     * Rejects a booking by its ID.
     *
     * @param id the ID of the booking to reject
     * @return rejected booking wrapped in RentalBookingsDTO
     */
    @PatchMapping("/reject/{id}")
    @PreAuthorize("hasRole('rental')")
    public ResponseEntity<RentalBookingsDTO> rejectBooking(@PathVariable int id) {
        return ResponseEntity.ok(new RentalBookingsDTO(service.rejectBooking(id)));
    }
}
