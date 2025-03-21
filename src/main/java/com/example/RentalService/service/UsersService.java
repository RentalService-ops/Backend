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
		return repo.findById(id).get();
	}
	
	public Users saveUser(Users user) {
		return repo.save(user);
	}
	
}
