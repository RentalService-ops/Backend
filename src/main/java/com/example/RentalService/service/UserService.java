package com.example.RentalService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RentalService.model.Users;
import com.example.RentalService.repo.UserRepositry;

import jakarta.servlet.http.HttpServletResponse;



@Service
public class UserService {

	@Autowired
	private UserRepositry repo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTService jwtService;
	
	 
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
	public List<Users> getAllusers(){
		return repo.findAll();
	}
	
//	public Users deleteByName(String name) {
//		
//		Users user  = repo.findByName(name);
//		
//		if(user != null) {
//			 repo.deleteById(user.getId());
//			 return user;
//		}
//		return null;
//	}

	
	public ResponseEntity<?> verify(Users user, HttpServletResponse response) {
	    Authentication authentication = authManager.authenticate(
	        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

	    if (authentication.isAuthenticated()) {
	        String jwtToken = jwtService.generateToken(user.getEmail());

	        // Create HttpOnly Cookie
	        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwtToken)
	                .httpOnly(true)  // ✅ Prevents JavaScript access
	                .secure(false)   // ❌ Set to true in production (for HTTPS)
	                .path("/")       // ✅ Makes cookie available to all endpoints
	                .maxAge(86400)   // ✅ 1 day expiration
	                .sameSite("Lax") // ✅ Helps with CSRF protection
	                .build();

	        // Add cookie to response
	        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

	        return ResponseEntity.ok("Login Successful");
	    }
	    return ResponseEntity.status(401).body("Invalid Credentials");
	}

}
