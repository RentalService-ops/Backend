package com.example.RentalService.serviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Override
	public Page<Users> getAllUsers(Pageable pageable) {
 		return repo.findAll(pageable);

	}

	@Override
	public Page<Users> getAllUsers(String search, Pageable pageable) {
		 if (search == null || search.trim().isEmpty()) {
             return repo.findAll(pageable);
         } else {
             return repo.searchByUsername(search, pageable);
         }
	}

	@Override
	public void deleteUser(int id) {
		if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }		
	}

	@Override
	public Users updateUser(int id, Users updatedUser) {
		return repo.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            return repo.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

	}
	
}
