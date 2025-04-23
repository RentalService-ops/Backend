package com.example.RentalService.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.RentalService.DTO.AdminBookingsDTO;
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

import jakarta.transaction.Transactional;


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

	
	
	@Override
	public List<Rental_Bookings> findByRentalId(int id){
		return rentalRepo.findByRenter_Id(id);
	}
	
	
	@Override
	public Rental_Bookings rejectBooking(int id) {
	    Rental_Bookings booking = rentalRepo.findById(id).orElseThrow();
	    booking.setStatus(BookingStatus.REJECTED);
	    rentalRepo.save(booking);

	    Equipment equipment = equipmentRepo.findByEquipmentId(booking.getEquipment().getEquipmentId());
	    String msg = "Your booking of " + equipment.getName() + " has been rejected.";
	    int userId = booking.getUser().getId();
	    
	    if(equipment.getQuantity() < booking.getEquipmentQuantity()) {
	    	msg="Booking rejected due to insufficient quantity available.";
	    }

	    Notification notificationEntity = Notification.builder()
	            .message(msg)
	            .userId(userId)
	            .timestamp(LocalDateTime.now())
	            .build();
	    notificationRepository.save(notificationEntity);

	    NotificationDTO notificationDTO = new NotificationDTO(msg);
	    messagingTemplate.convertAndSend("/topic/booking/" + userId, notificationDTO);

	    return booking;
	}


	@Override
	@Transactional
	public Rental_Bookings approveBooking(int id) {
	    Rental_Bookings booking = rentalRepo.findById(id).orElseThrow();

	    Equipment equipment = equipmentRepo.findById(booking.getEquipment().getEquipmentId())
	            .orElseThrow();
	    
	    if(equipment.getQuantity() < booking.getEquipmentQuantity()) {
	    	return rejectBooking(id);
	    }
	    equipment.setQuantity(equipment.getQuantity() - booking.getEquipmentQuantity());
	    equipmentRepo.save(equipment);
	    
	    booking.setStatus(BookingStatus.APPROVED);

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


    @Override
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
    @Override
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
    @Override
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


	@Override
	public boolean updateBookingStatus(int id, BookingStatus status) {
		Optional<Rental_Bookings> bookingOptional = rentalRepo.findById(id);
		if (bookingOptional.isPresent()) {
			Rental_Bookings booking = bookingOptional.get();
			booking.setStatus(status);
			rentalRepo.save(booking);
			return true;
		}
		return false;
	}


	@Override
	public boolean deleteBooking(int id) {
		Optional<Rental_Bookings> bookingOptional = rentalRepo.findById(id);
		if (bookingOptional.isPresent()) {
			rentalRepo.deleteById(id);
			return true;
		}
		return false;
	}


	@Override
	public Page<AdminBookingsDTO> getAllBookingsBySearch(String searchBy, Pageable pageable) {
		List<Object[]> bookings = new ArrayList<Object[]>();
		String search= searchBy !=null ? searchBy : "Renter";
		long totalBookings=0;
		if(search.equals("User")){
			bookings=rentalRepo.findBookingBySearchUser(pageable);
			totalBookings=rentalRepo.countDistinctUserId();
		}
		else if(search.equals("Renter")) {
			bookings=rentalRepo.findBookingBySearchRenter(pageable);
			totalBookings=rentalRepo.countDistinctRentalId();
		}
		else if(search.equals("Equipment")) {
			bookings=rentalRepo.findBookingBySearchEquipment(pageable);
			totalBookings=rentalRepo.countDistinctEquipmentId();
		}
		System.out.println(totalBookings);
		Page<AdminBookingsDTO> bookingsDTO = new PageImpl<>(
			    bookings.stream()
			        .map(booking -> new AdminBookingsDTO(
			             getName(((Number) booking[0]).intValue(),search),
			            ((Number) booking[1])!= null ? ((Number) booking[1]).intValue() : 0,
	            		((Number) booking[2])!= null ? ((Number) booking[2]).intValue() : 0,
        				((Number) booking[3])!= null ? ((Number) booking[3]).intValue() : 0,
						((Number) booking[4])!= null ? ((Number) booking[4]).intValue() : 0,
						((Number) booking[5])!= null ? ((Number) booking[5]).intValue() : 0,
						((Number) booking[6])!= null ? ((Number) booking[6]).intValue() : 0
			        ))
			        .collect(Collectors.toList()),pageable,totalBookings
			);


		return bookingsDTO;
	}
	
	private String getName(int id,String searchBy) {
		if(searchBy.equals("Equipment")) {
			return equipmentRepo.findById(id).get().getName();
		}
		return userRepositry.findById(id).get().getUsername();
	}


	@Override
	public ResponseEntity<Map<String, Object>> getAllBookingByAdmin() {

		int count=0;
		List<Rental_Bookings> bookings = rentalRepo.findAll().reversed();
		count=bookings.size();
		Map<String, Object> response=new HashMap<>();
		response.put("totalBookings", count);
		response.put("recentbookings",bookings.subList(0,4).stream().map(booking -> new RentalBookingsDTO(booking)).collect(Collectors.toList()));
		
		return ResponseEntity.ok(response);
	}
	
	@Async
	@Transactional
	@Scheduled(cron = "0 0 0/12 * * ?")//Scheduling method execution after every 12 hours
	public void autoCompleteBookings() {
		System.out.println("Cron job executed");
	    List<Rental_Bookings> bookings = rentalRepo.findByEndDateBeforeAndIsReturnedFalse(LocalDate.now());
	    if(bookings != null) {
		    for (Rental_Bookings booking : bookings) {
		        booking.setReturned(false);
		        booking.setStatus(BookingStatus.COMPLETED);
		        rentalRepo.save(booking);
	
		        Equipment equipment = booking.getEquipment();
		        equipment.setQuantity(equipment.getQuantity() + booking.getEquipmentQuantity());
		        equipmentRepo.save(equipment);
		    }
	    }
	}


	@Override
	public ResponseEntity<?> returnEquipment(int bookingId) {
		Rental_Bookings booking = rentalRepo.findById(bookingId)
		        .orElseThrow(() -> new IllegalArgumentException("Booking not found.Either booking id specified is null or booking with specified id does not exist."));

		    if (booking.isReturned()) {
		        return ResponseEntity.badRequest().body("Equipment already returned");
		    }

		    if (LocalDate.now().isBefore(booking.getEndDate())) {
		        return ResponseEntity.badRequest().body("Cannot return before end date");
		    }

		    booking.setReturned(true);
		    booking.setStatus(BookingStatus.COMPLETED);
		    rentalRepo.save(booking);

		    Equipment equipment = booking.getEquipment();
		    equipment.setQuantity(equipment.getQuantity() + booking.getEquipmentQuantity());
		    equipmentRepo.save(equipment);
		return ResponseEntity.ok("Equipment marked as returned");
	}

	
}
