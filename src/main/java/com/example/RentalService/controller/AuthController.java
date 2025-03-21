package com.example.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.model.Users;
import com.example.RentalService.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

/*
 * Controller for managing Authentication and Signup for User, Renter, Admin
 * 
 * **/
@RestController
@RequestMapping("")
@CrossOrigin
public class AuthController {

	//Service class for database interaction.
	@Autowired
	private AuthService service;
	
	//Response object.
	@Autowired
	HttpServletResponse response;
	
	/**
	 * Handles User registration
	 * @Param user: The user details of the user wanting to register.
	 * Returns the registered user.
	 * */
	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		return service.register(user);
	}
	
	/**
	 * Handles User login.
	 * @Param user: The user details of the user wanting to login
	 * Returns the logged in user after successful login completion else return error.
	 * */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users user) {
		
		return service.verify(user,response);
	}
}
