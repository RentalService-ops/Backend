package com.example.RentalService.serviceImpl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.RentalService.Exceptions.WrongUserEmailException;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.UserRepository;
import com.example.RentalService.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;



@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private UserRepository repo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTServiceImpl jwtService;
	
	@Autowired
    private JavaMailSender mailSender; 
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Override
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
	@Override
	public List<Users> getAllusers(){
		return repo.findAll();
	}
	
	@Override
	public Users findUsreById(int id) throws IllegalArgumentException{
		return repo.findById(id).get();
	}
	@Override
	public ResponseEntity<?> verify(@RequestBody Users user, HttpServletResponse response) {
		
	    Authentication authentication = authManager.authenticate(
	        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

	    if (authentication.isAuthenticated()) {

	        Users userDemo = repo.findByEmail(user.getEmail()).get();
	        String jwtToken = jwtService.generateToken(String.valueOf(userDemo.getId()), userDemo.getEmail(), userDemo.getRole().toLowerCase());

	        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwtToken)
	                .httpOnly(false)
	                .secure(false)
	                .path("/")
	                .maxAge(86400)
	                .sameSite("Lax")
	                .build();

	        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

	        Map<String, Object> responseBody = new HashMap<>();
	        responseBody.put("message", "Login successful");

	        return ResponseEntity.ok(responseBody);
	    }

	    return ResponseEntity.status(401).body(Map.of("error", "Invalid Credentials"));
	}


	@Override
	public void sendOTP(String useremail) {
		if(useremail==null) {
			throw new IllegalArgumentException("Specify email.");
		}
		Users user=repo.findByEmail(useremail).get();
		if(user==null) {
			throw new WrongUserEmailException("Email is not registered.");
		}
		Double otpvalue=Math.random()*1000000 + 100000;
		if(otpvalue > 999999) {
			otpvalue=otpvalue/10;
		}
		BigInteger otp= BigInteger.valueOf(otpvalue.longValue());
		user.setOTP(otp);
		
	    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("vipulsahani7600@gmail.com");
        simpleMailMessage.setTo(useremail);
        simpleMailMessage.setSubject("OTP verification");
        simpleMailMessage.setText("Here is your OTP for verification: "+otp);

        this.mailSender.send(simpleMailMessage);
        
		repo.save(user);
	}

	@Override
	public boolean verifyOTP(BigInteger sentOTP,String email) {
		if(email==null) {
			System.out.println(email);
			throw new IllegalArgumentException("Specify email.");
		}
		
		Users user=repo.findByEmail(email).get();
		if(user==null) {
			return false;
		}

		if(sentOTP.equals(user.getOTP())) {
			System.out.println("true and true");
			user.setOTP(null);
			repo.save(user);
			return true;
		}
		user.setOTP(null);
		repo.save(user);
		return false;
	}

	@Override
	public void resetPassword(String password, String email) {
		// TODO Auto-generated method stub
		if(email==null) {
			throw new IllegalArgumentException("Specify email.");
		}
		Users user=repo.findByEmail(email).get();
		if(user==null) {
			throw new WrongUserEmailException("Email is not registered.");
		}
		user.setPassword(encoder.encode(password));
		repo.save(user);
	}

}
