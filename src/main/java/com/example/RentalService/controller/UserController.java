package com.example.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.model.Users;
import com.example.RentalService.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class UserController {

	
	@Autowired
	private UserService service;
	
	@Autowired
	HttpServletResponse response;
	
	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		return service.register(user);
	}
	
	@GetMapping("/")
	public String homePge() {
		return "Hello...";
	}
	
//	@DeleteMapping("/user/{name}")
//	public Users deleteByName(@PathVariable String name) {
//		return service.deleteByName(name);
//	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users user) {
		
		return service.verify(user,response);
	}
}
