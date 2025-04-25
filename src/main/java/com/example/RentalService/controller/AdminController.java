package com.example.RentalService.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.AdminBookingsDTO;
import com.example.RentalService.DTO.CategoryDTO;
import com.example.RentalService.DTO.EquipmentDTO;
import com.example.RentalService.model.BookingStatus;
import com.example.RentalService.model.CustomerQuery;
import com.example.RentalService.model.Users;
import com.example.RentalService.service.CategoryService;
import com.example.RentalService.service.CustomerQueryService;
import com.example.RentalService.service.EquipmentService;
import com.example.RentalService.service.PaymentService;
import com.example.RentalService.service.RentalBookingService;
import com.example.RentalService.service.UsersService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('admin')")
public class AdminController {
	
	//Service to perform operations on customerQuery data.
	@Autowired
	private CustomerQueryService queryService;
	
	//Service to perform operations on user data.
	@Autowired
	private UsersService userService;
	
	//Service to perform operations on Equipment data.
	@Autowired
	private EquipmentService equipmentService;
	
	//Service to perform operations on booking data.
	@Autowired
	private RentalBookingService rentalBookingService;
	
	//Service to perform operations on Category data.
	@Autowired
	private CategoryService categoryService;
	
	//Service to perform opertations on payment data.
	@Autowired
	private PaymentService paymentService;

	public AdminController(UsersService userService) {
		this.userService = userService;
	}

	/**
	 * Retrieves a paginated list of all users, optionally filtered by search query.
	 *
	 * @param page     page number
	 * @param size     page size
	 * @param sortBy   property to sort by
	 * @param direction sort direction (asc/desc)
	 * @param search   optional search query
	 * @return paginated users
	 */
	@GetMapping("/users")
	public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "04") int size, @RequestParam(defaultValue = "username") String sortBy,
			@RequestParam(defaultValue = "asc") String direction, @RequestParam(required = false) String search) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
		Page<Users> users = userService.getAllUsers(search, pageable);

		Map<String, Object> response = new HashMap<>();
		response.put("content", users.getContent());
		response.put("currentPage", users.getNumber());
		response.put("totalItems", users.getTotalElements());
		response.put("totalPages", users.getTotalPages());

		return ResponseEntity.ok(response);
	}

	/**
	 * Deletes a user by ID and all the details related to user.
	 *
	 * @param id user ID
	 * @return HTTP 204 if successful, 404 if not found
	 */
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable int id) {
		Users user = userService.getUserByUserId(id);
		if (user != null) {
			
			//Deleting all the payments related to user.
			paymentService.deletePaymentByUserId(id);
			
			//Deleting all the bookings related to user.
			rentalBookingService.deleteBookingByUserId(id);
			
			//Deleting all the equipments related to user.
			equipmentService.deleteEquipmentByUserId(id);
			
			//Deleting all the categories related to user.
			categoryService.deleteCategoryByUserId(id);
			
			//Deleting the user
			userService.deleteUser(id);
			
			return ResponseEntity.status(204).body("User successfully deleted.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}


	/**
	 * Retrieves all unresolved customer queries.
	 *
	 * @return list of unresolved queries
	 */
	@GetMapping("/queries/pending")
	public ResponseEntity<List<CustomerQuery>> getPendingQueries() {
		return ResponseEntity.ok(queryService.getPendingQueries());
	}

	/**
	 * Marks a customer query as resolved.
	 *
	 * @param id query ID
	 * @return success or error message
	 */
	@PutMapping("/queries/{id}/resolve")
	public ResponseEntity<String> resolveQuery(@PathVariable int id) {
		boolean updated = queryService.resolveQuery(id);
		if (updated) {
			return ResponseEntity.ok("Query marked as resolved.");
		} else {
			return ResponseEntity.badRequest().body("Query not found or already resolved.");
		}
	}

	/**
	 * Retrieves paginated list of rental equipment.
	 *
	 * @param page     page number
	 * @param size     page size
	 * @param sortBy   property to sort by
	 * @param direction sort direction
	 * @param search   optional search term
	 * @return paginated equipment list
	 */
	@GetMapping("/equipment")
	public ResponseEntity<Map<String, Object>> getAllEquipment(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String direction, @RequestParam(required = false) String search) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

		Page<EquipmentDTO> equipmentPage;
		if (search != null && !search.isEmpty()) {
			equipmentPage = equipmentService.searchEquipmentByName(search, pageable);
		} else {
			equipmentPage = equipmentService.getAllEquipment(pageable);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("content", equipmentPage.getContent());
		response.put("currentPage", equipmentPage.getNumber());
		response.put("totalItems", equipmentPage.getTotalElements());
		response.put("totalPages", equipmentPage.getTotalPages());

		return ResponseEntity.ok(response);
	}

	/**
	 * Retrieves paginated rental bookings.
	 *
	 * @param page     page number
	 * @param size     page size
	 * @param sortBy   property to sort by
	 * @param direction sort direction
	 * @param search   optional search term
	 * @return paginated booking data
	 */
	@GetMapping("/bookings")
	public ResponseEntity<Map<String, Object>> getAllBookings(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "desc") String direction, @RequestParam(required = false) String search) {
		Pageable pageable = PageRequest.of(page, size);
		Page<AdminBookingsDTO> bookings = rentalBookingService.getAllBookingsBySearch(search,pageable);

		Map<String, Object> response = new HashMap<>();
		response.put("content", bookings);
		response.put("currentPage", bookings.getNumber());
		response.put("totalItems", bookings.getTotalElements());
		response.put("totalPages", bookings.getTotalPages());

		return ResponseEntity.ok(response);
	}

	/**
	 * Updates the status of a booking.
	 *
	 * @param id booking ID
	 * @param requestBody map containing the new status
	 * @return success or error message
	 */
	@PutMapping("/bookings/{id}")
	public ResponseEntity<String> updateBookingStatus(@PathVariable int id,
			@RequestBody Map<String, String> requestBody) {
		String status = requestBody.get("status");

		if (status == null) {
			return ResponseEntity.badRequest().body("Status is required.");
		}

		boolean updated = rentalBookingService.updateBookingStatus(id, BookingStatus.valueOf(status));
		if (updated) {
			return ResponseEntity.ok("Booking status updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found.");
		}
	}

	/**
	 * Deletes a booking by ID.
	 *
	 * @param id booking ID
	 * @return success or error message
	 */
	@DeleteMapping("/bookings/{id}")
	public ResponseEntity<String> deleteBooking(@PathVariable int id) {
		boolean deleted = rentalBookingService.deleteBooking(id);
		if (deleted) {
			return ResponseEntity.ok("Booking deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found.");
		}
	}

	/**
	 * Retrieves paginated list of equipment categories.
	 *
	 * @param page     page number
	 * @param size     page size
	 * @param sortBy   property to sort by
	 * @param direction sort direction
	 * @return paginated category list
	 */
	@GetMapping("/categories")
	public ResponseEntity<Map<String, Object>> getAllCategories(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String direction,@RequestParam(defaultValue="") String search) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
		Page<CategoryDTO> categoryPage = categoryService.getAllCategories(pageable,search).map((category)-> new CategoryDTO(category));

		Map<String, Object> response = new HashMap<>();
		response.put("content", categoryPage.getContent());
		response.put("currentPage", categoryPage.getNumber());
		response.put("totalItems", categoryPage.getTotalElements());
		response.put("totalPages", categoryPage.getTotalPages());

		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retrieves all the bookings and returns the latest bookings.
	 * */
	@GetMapping("/getAllBookings")
	public ResponseEntity<?> getAllBookings(){
		return rentalBookingService.getAllBookingByAdmin();
	}
}
