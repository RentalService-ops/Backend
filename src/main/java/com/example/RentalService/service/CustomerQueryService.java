package com.example.RentalService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RentalService.model.CustomerQuery;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.CustomerQueryRepository;
import com.example.RentalService.repo.UserRepository;

@Service
public class CustomerQueryService {
	
	private CustomerQueryRepository queryRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	CustomerQueryService(CustomerQueryRepository repo){
		this.queryRepo=repo;
	}
	
	public void saveQuery(CustomerQuery query) {
		query.setQueryStatus("pending");
		try {
		Users user=userRepo.findByEmail(query.getUseremail()).get();
		if(user!=null) {
		query.setUser(user);
		}
		}
		catch(Exception e) {
			
		}
		queryRepo.save(query);
	}
}
