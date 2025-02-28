package com.example.RentalService.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.RentalService.model.Users;

public class UserPrinciple implements UserDetails {


	private Users user;
	
	public UserPrinciple(Users user) {
		this.user = user;
	}
	
	 @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
	    }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

}
