package com.example.RentalService.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.example.RentalService.DTO.NotificationDTO;
import com.example.RentalService.DTO.RentalBookingsDTO;
import com.example.RentalService.model.BookingStatus;
import com.example.RentalService.model.Equipment;
import com.example.RentalService.model.Notification;
import com.example.RentalService.model.Rental_Bookings;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.EquipmentRepo;
import com.example.RentalService.repo.NotificationRepository;
import com.example.RentalService.repo.RentalBookingRepository;
import com.example.RentalService.repo.UserRepository;
import com.example.RentalService.service.RentalBookingService;


@Service
public class RentalBookingServiceImpl implements RentalBookingService{
	
	@Autowired
	RentalBookingRepository rentalRepo;
	
	@Autowired
	EquipmentRepo equipmentRepo;
	
	@Autowired
	UserRepository userRepositry;
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	private NotificationRepository notificationRepository;

	
	
	public List<Rental_Bookings> findByRentalId(int id){
		return rentalRepo.findByRenter_Id(id);
	}
	
	
	public Rental_Bookings rejectBooking(int id) {
	    Rental_Bookings booking = rentalRepo.findById(id).orElseThrow();
	    booking.setStatus(BookingStatus.REJECTED);
	    rentalRepo.save(booking);

	    Equipment equipment = equipmentRepo.findByEquipmentId(booking.getEquipment().getEquipmentId());
	    String msg = "Your booking of " + equipment.getName() + " has been rejected.";
	    int userId = booking.getUser().getId();

	    // Save to database
	    Notification notificationEntity = Notification.builder()
	            .message(msg)
	            .userId(userId)
	            .timestamp(LocalDateTime.now())
	            .build();
	    notificationRepository.save(notificationEntity);

	    // Send WebSocket notification
	    NotificationDTO notificationDTO = new NotificationDTO(msg);
	    messagingTemplate.convertAndSend("/topic/booking/" + userId, notificationDTO);

	    return booking;
	}


	
	public Rental_Bookings approveBooking(int id) {
	    Rental_Bookings booking = rentalRepo.findById(id).orElseThrow();
	    booking.setStatus(BookingStatus.APPROVED);

	    Equipment equipment = equipmentRepo.findById(booking.getEquipment().getEquipmentId())
	            .orElseThrow();
	    equipment.setQuantity(equipment.getQuantity() - booking.getEquipment_quantity());
	    equipmentRepo.save(equipment);

	    rentalRepo.save(booking);

	    String msg = "Your booking of " + equipment.getName() + " has been approved!";
	    int userId = booking.getUser().getId();

	    // Save to database
	    Notification notificationEntity = Notification.builder()
	            .message(msg)
	            .userId(userId)
	            .timestamp(LocalDateTime.now())
	            .build();
	    notificationRepository.save(notificationEntity);

	    // Send WebSocket notification
	    NotificationDTO notificationDTO = new NotificationDTO(msg);
	    messagingTemplate.convertAndSend("/topic/booking/" + userId, notificationDTO);

	    return booking;
	}


	
	/**
     * Creates a new booking.
     */


    public ResponseEntity<?> equipmentBooking(Rental_Bookings booking) {
        if (booking == null) {
            return ResponseEntity.badRequest().body("Booking request cannot be null");
        }

        Equipment equipment = equipmentRepo.findById(booking.getEquipment().getEquipmentId())
                .orElse(null);

        if (equipment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found");
        }

        if (equipment.getUser() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Equipment is not associated with any user");
        }

        Users user = userRepositry.findById(equipment.getUser().getId())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        booking.setEquipment(equipment);
        booking.setRenter(user);
        booking.setStatus(booking.getStatus());
        booking.setTotalPrice(booking.getTotalPrice());
        Rental_Bookings savedBooking = rentalRepo.save(booking);
        RentalBookingsDTO bookingDTO = new RentalBookingsDTO(
            savedBooking
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
    }
    
    /**
     * Retrieves booking details by user ID.
     */
    public ResponseEntity<?> getBookingDetailsByUserId(int id) {
        List<Rental_Bookings> bookings = rentalRepo.findByUser_Id(id);
        
        if (!bookings.isEmpty()) {
            List<RentalBookingsDTO> bookingDTOs = bookings.stream()
                .map((booking)->new RentalBookingsDTO(booking))
                .collect(Collectors.toList());
            return ResponseEntity.ok(bookingDTOs);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No bookings found for user ID: " + id);
    }
    
    /**
     * Cancels a booking if it is still pending.
     */
    public ResponseEntity<?> cancelBooking(int bookingId) {
        Optional<Rental_Bookings> bookingOptional = rentalRepo.findById(bookingId);
        
        if (bookingOptional.isPresent()) {
        	Rental_Bookings booking = bookingOptional.get();
            
            if (booking.getStatus() == BookingStatus.PENDING) {
                booking.setStatus(BookingStatus.CANCELLED);
                rentalRepo.save(booking);
                return ResponseEntity.ok("Booking Cancelled Successfully");
            } else {
                return ResponseEntity.badRequest().body("Only pending bookings can be cancelled.");
            }
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found.");
    }
}
