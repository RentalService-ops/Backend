package com.example.RentalService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.RentalService.model.Users;
import com.example.RentalService.repo.UserRepositry;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositry repo;

    public MyUserDetailsService(UserRepositry repo) {
    	this.repo = repo;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
    	System.out.println(email);
        Users user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // If role is stored in the User entity, dynamically assign the role
        String role = user.getRole() != null ? user.getRole() : "USER"; // Use role from user entity or default to "USER"

        // Build and return the UserDetails object
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())  // Assuming `email` is used for username
                .password(user.getPassword())  // Get the password from the User entity
                .roles(role)  // Set the dynamic role from the user entity
                .build();
    }
}
