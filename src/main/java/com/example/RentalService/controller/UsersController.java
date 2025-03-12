package com.example.RentalService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.UsersDTO;
import com.example.RentalService.model.Users;
import com.example.RentalService.service.UsersService;


/**
 * Controller for handling Rental operations
 * */
@RestController
@RequestMapping("/api/")
public class UsersController {
	
	private UsersService service;
	
	public UsersController(UsersService service) {
		this.service=service;
	}
	
	@GetMapping(value={"user/getUser","rental/getRenter"})
	public UsersDTO getUserByUserId(@RequestParam("id") int id) {
		return new UsersDTO(service.getUserByUserId(id));
	}
	
	
	@PostMapping(value={"user/updateUser","rental/updateRenter"})
	public UsersDTO updateUserbyId(@RequestParam("id") int id, @RequestBody UsersDTO userDTO) {
		Users user=service.getUserByUserId(id);
		user.setAddress(userDTO.getAddress());
		user.setEmail(userDTO.getEmail());
		user.setPhoneNumber(userDTO.getPhoneNo());
		user.setUsername(userDTO.getUsername());
		return new UsersDTO(service.saveUser(user));
	}
}
