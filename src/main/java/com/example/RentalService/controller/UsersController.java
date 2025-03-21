package com.example.RentalService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.UsersDTO;
import com.example.RentalService.model.CustomerQuery;
import com.example.RentalService.model.Users;
import com.example.RentalService.service.CustomerQueryService;
import com.example.RentalService.service.UsersService;


/**
 * Controller for handling User operations
 * */
@RestController
@RequestMapping("/api/user")
public class UsersController {
	
	//Service for Users Entity interaction
	private UsersService service;
	
	//Service for CustomerQuery entity interaction.
	private CustomerQueryService customerService;
	
	/**
	 * Injecting UserService and CustomerService dependency using Constructor dependency injection.
	 * */
	public UsersController(UsersService service,CustomerQueryService customerService) {
		this.service=service;
		this.customerService=customerService;
	}
	
	/**
	 * @Param id: id of the User whose user details are to be fetched.
	 * Returns the user,renter with the corresponding id provided.
	 * */
	@GetMapping(value={"/getUser"})
	@PreAuthorize("hasRole('rental') or hasRole('admin') or hasRole('user')")
	public UsersDTO getUserByUserId(@RequestParam("id") int id) {
		return new UsersDTO(service.getUserByUserId(id));
	}
	
	/**
	 * Updates the User with corresponding id provided with corresponding 
	 * provided updated user details
	 * @Param id: id of the user to be updated
	 * @Param userDTO: Updated user details provided.
	 * Returns the Users object with updated details.
	 * */
	@PostMapping(value={"/updateUser"})
	@PreAuthorize("hasRole('rental') or hasRole('admin') or hasRole('user')")
	public UsersDTO updateUserbyId(@RequestParam("id") int id, @RequestBody UsersDTO userDTO) {
		Users user=service.getUserByUserId(id);
		user.setEmail(userDTO.getEmail());
		user.setPhoneNumber(userDTO.getPhoneNo());
		user.setUsername(userDTO.getUsername());
		return new UsersDTO(service.saveUser(user));
	}
	
	
	/**
	 * Saves the query posted by users into database.
	 * @Param query: The query posted by user.
	 * Returns success message
	 * */
	@PostMapping("/contact")
	public ResponseEntity<?> saveQuery(@RequestBody CustomerQuery query){
		customerService.saveQuery(query);
		return new ResponseEntity<String>("query saved",HttpStatus.CREATED);
	}
}
