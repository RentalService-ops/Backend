package com.example.RentalService.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.RentalService.model.Users;
import com.example.RentalService.repo.UserRepository;
import com.example.RentalService.service.UsersService;


@Service
public class UsersServiceImpl implements UsersService{
	
	private UserRepository repo;
	
	public UsersServiceImpl(UserRepository repo){
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
