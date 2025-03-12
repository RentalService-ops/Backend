package com.example.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.model.CustomerQuery;
import com.example.RentalService.model.Users;
import com.example.RentalService.service.CustomerQueryService;
import com.example.RentalService.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("")
@CrossOrigin
public class AuthController {

	
	@Autowired
	private AuthService service;
	
	@Autowired
	HttpServletResponse response;
	
	@Autowired
	private CustomerQueryService customerService;
	
	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		return service.register(user);
	}
	
	@GetMapping("/")
	public String homePge() {
		return "Hello...";
	}
	
	 @GetMapping("/check-role")
	    public String checkRole() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        return "User: " + authentication.getName() + ", Roles: " + authentication.getAuthorities();
	    }
	
//	@DeleteMapping("/user/{name}")
//	public Users deleteByName(@PathVariable String name) {
//		return service.deleteByName(name);
//	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users user) {
		
		return service.verify(user,response);
	}
	
	@PostMapping("/contact")
	public ResponseEntity<?> saveQuery(@RequestBody CustomerQuery query){
		customerService.saveQuery(query);
		return ResponseEntity.ok("query saved");
	}
}
