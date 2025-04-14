package com.example.RentalService.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.UserRepository;
import com.example.RentalService.service.UsersService;


@Service
public class UsersServiceImpl implements UsersService{
	
	private UserRepository repo;
	
	public UsersServiceImpl(UserRepository repo){
		this.repo=repo;
	}
	
	@Override
	public Users getUserByUserId(int id) throws UserNotFoundException{
		Users user=repo.findById(id).get();
		if(user == null) {
			throw new UserNotFoundException("User with specified id does not exist or id is null");
		}
		return user;
	}
	
	@Override
	public Users saveUser(Users user) {
		return repo.save(user);
	}
	
}
