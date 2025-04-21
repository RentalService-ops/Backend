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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.AdminBookingsDTO;
import com.example.RentalService.DTO.CategoryDTO;
import com.example.RentalService.DTO.UsersDTO;
import com.example.RentalService.model.BookingStatus;
import com.example.RentalService.model.Category;
import com.example.RentalService.model.CustomerQuery;
import com.example.RentalService.model.Equipment;
import com.example.RentalService.model.Users;
import com.example.RentalService.service.CategoryService;
import com.example.RentalService.service.CustomerQueryService;
import com.example.RentalService.service.EquipmentService;
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
	 * Deletes a user by ID.
	 *
	 * @param id user ID
	 * @return HTTP 204 if successful, 404 if not found
	 */
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
		Users user = userService.getUserByUserId(id);
		if (user != null) {
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Updates a user by ID.
	 *
	 * @param id      user ID
	 * @param userDTO updated user data
	 * @return updated user DTO or 404 if user not found
	 */
	@PutMapping("/users/{id}")
	public ResponseEntity<UsersDTO> updateUserById(@PathVariable int id, @RequestBody UsersDTO userDTO) {
		Users user = userService.getUserByUserId(id);
		if (user != null) {
			user.setEmail(userDTO.getEmail());
			user.setPhoneNumber(userDTO.getPhoneNo());
			user.setUsername(userDTO.getUsername());
			return ResponseEntity.ok(new UsersDTO(userService.saveUser(user)));
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

		Page<Equipment> equipmentPage;
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

		System.out.println("Fetching equipment");
		return ResponseEntity.ok(response);
	}

	/**
	 * Deletes equipment by ID.
	 *
	 * @param id equipment ID
	 * @return success or error message
	 */
	@DeleteMapping("/equipment/{id}")
	public ResponseEntity<String> deleteEquipment(@PathVariable int id) {
		Equipment equipment = equipmentService.getEquipmentById(id);
		if (equipment != null) {
			equipmentService.deleteEquipment(id);
			return ResponseEntity.ok("Equipment deleted successfully.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found.");
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
	 * Adds a new equipment category.
	 *
	 * @param categoryDTO the new category data
	 * @return created category DTO
	 */
	@PostMapping("/categories")
	public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		category.setDescription(categoryDTO.getDescription());

		Category savedCategory = categoryService.addCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryDTO(savedCategory));
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
			@RequestParam(defaultValue = "asc") String direction) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
		Page<Category> categoryPage = categoryService.getAllCategories(pageable);

		Map<String, Object> response = new HashMap<>();
		response.put("content", categoryPage.getContent());
		response.put("currentPage", categoryPage.getNumber());
		response.put("totalItems", categoryPage.getTotalElements());
		response.put("totalPages", categoryPage.getTotalPages());

		return ResponseEntity.ok(response);
	}

	/**
	 * Deletes a category by ID.
	 *
	 * @param id category ID
	 * @return success or error message
	 */
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable int id) {
		ResponseEntity<?> response = categoryService.deleteCategory(id);
		if (response.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok("Category deleted successfully.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
	}
	
	@GetMapping("/getAllBookings")
	public ResponseEntity<?> getAllBookings(){
		return rentalBookingService.getAllBookingByAdmin();
	}
}
