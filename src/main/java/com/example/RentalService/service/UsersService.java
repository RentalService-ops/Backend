package com.example.RentalService.service;

import org.springframework.stereotype.Service;

import com.example.RentalService.model.Users;
import com.example.RentalService.repo.UserRepository;


@Service
public class UsersService {
	
	private UserRepository repo;
	
	public UsersService(UserRepository repo){
		this.repo=repo;
	}
	
	public Users getUserByUserId(int id) {
		Users user=repo.findById(id).get();
		return user;
	}
	
	public Users saveUser(Users user) {
		return repo.save(user);
	}
	
}
