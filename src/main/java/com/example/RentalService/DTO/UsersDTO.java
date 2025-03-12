package com.example.RentalService.DTO;

import java.math.BigInteger;

import com.example.RentalService.model.Users;

public class UsersDTO {
	private String username;
	private BigInteger phoneNo;
	private String email;
	private String address;
	
	public UsersDTO(Users user) {
		this.setUsername(user.getUsername());
		this.setAddress(user.getAddress());
		this.setEmail(user.getEmail());
		this.setPhoneNo(user.getPhoneNumber());
	}
	
	public UsersDTO() {
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BigInteger getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(BigInteger phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
