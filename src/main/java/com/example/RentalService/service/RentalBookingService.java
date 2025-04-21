package com.example.RentalService.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.RentalService.DTO.AdminBookingsDTO;
import com.example.RentalService.model.BookingStatus;
import com.example.RentalService.model.Rental_Bookings;

public interface RentalBookingService {

    /**
     * Find rental bookings by renter's user ID.
     * 
     * @param id the user ID of the renter
     * @return a list of rental bookings associated with the renter's ID
     */
    List<Rental_Bookings> findByRentalId(int id);

    /**
     * Reject a booking by its ID.
     * 
     * @param id the ID of the booking to reject
     * @return the rejected booking
     */
    Rental_Bookings rejectBooking(int id);

    /**
     * Approve a booking by its ID and update the equipment quantity.
     * 
     * @param id the ID of the booking to approve
     * @return the approved booking
     */
    Rental_Bookings approveBooking(int id);

    /**
     * Create a new equipment booking.
     * 
     * @param booking the booking details
     * @return a ResponseEntity containing the status and created booking information
     */
    ResponseEntity<?> equipmentBooking(Rental_Bookings booking);

    /**
     * Retrieve booking details by user ID.
     * 
     * @param id the user ID to retrieve the booking details for
     * @return a ResponseEntity containing the list of booking details for the user
     */
    ResponseEntity<?> getBookingDetailsByUserId(int id);

    /**
     * Cancel a booking by its ID if it is still in the pending status.
     * 
     * @param bookingId the ID of the booking to cancel
     * @return a ResponseEntity containing the status of the cancellation
     */
    ResponseEntity<?> cancelBooking(int bookingId);

    /**
     * Search and paginate all bookings for admin view.
     * 
     * @param search the search keyword (e.g., by username, booking ID)
     * @param pageable pagination information (page number, size, sort)
     * @return a paginated list of bookings matching the search criteria
     */
    Page<AdminBookingsDTO> getAllBookingsBySearch(String search, Pageable pageable);

    /**
     * Count the total number of bookings associated with a specific renter.
     * 
     * @param renterUsername the username of the renter
     * @return the count of bookings
     */
    long countBookingsByRenterUsername(String renterUsername);

    /**
     * Update the status of a booking.
     * 
     * @param id the ID of the booking
     * @param valueOf the new booking status
     * @return true if the update was successful, false otherwise
     */
    boolean updateBookingStatus(int id, BookingStatus valueOf);

    /**
     * Delete a booking by its ID.
     * 
     * @param id the ID of the booking to delete
     * @return true if the deletion was successful, false otherwise
     */
    boolean deleteBooking(int id);
    
    ResponseEntity<Map<String, Object>> getAllBookingByAdmin();
}
