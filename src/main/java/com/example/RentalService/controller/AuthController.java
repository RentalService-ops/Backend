package com.example.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.ResetPasswordDTO;
import com.example.RentalService.DTO.UsersDTO;
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
	public ResponseEntity<UsersDTO> register(@RequestBody Users user) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new UsersDTO(service.register(user)));
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
	
    /**
     * Sends OTP via email to specified user email address.
     * @param useremail the email of the user where the OTP is to be sent.
     * 
     * */
	@GetMapping("/otp")
	public ResponseEntity<?> sendOTP(@RequestParam("useremail") String useremail){
		service.sendOTP(useremail);
		return ResponseEntity.ok("");
	}
	
    /**
     * Verifies OTP sent by the user.
     * @param resetPassword contains details such as OTP to be verified and email of the user whose OTP is to be verified.
     * */
	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOTP(@RequestBody ResetPasswordDTO resetPassword){
		if(service.verifyOTP(resetPassword.getSentOTP(),resetPassword.getEmail())) {
			return ResponseEntity.ok("OTP verified");
		}
		return ResponseEntity.status(401).body("Wrong OTP entered.");
		
	}
	
    /**
     * Resets the password as specified by user.
     * @param password the new password to be reset
     * */
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetPassword){
		service.resetPassword(resetPassword.getResetPassword(),resetPassword.getEmail());
		return ResponseEntity.ok("Password has been reset.");
	}
}
