package com.example.RentalService.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.RentalService.DTO.CategoryDTO;
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Category;
import com.example.RentalService.model.Equipment;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.CategoryRepo;
import com.example.RentalService.repo.EquipmentRepo;
import com.example.RentalService.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private EquipmentRepo equipmentRepo;
	
	@Autowired
	private AuthServiceImpl userService;
		
	@Override
	public Category addCategory(Category category) throws IllegalArgumentException,UserNotFoundException{

	    if (category.getUser() == null || category.getUser().getId() == 0) {
	        throw new IllegalArgumentException("User ID is required but was null or 0.");
	    }

	    // Fetch the user from DB
	    Users user = userService.findUsreById(category.getUser().getId());

	    if (user == null) {
	        throw new UserNotFoundException("User with ID " + category.getUser().getId() + " not found.");
	    }

	    category.setUser(user); // Assign the fetched user
	    return repo.save(category);
	}

	
	@Override
	public ResponseEntity<?> deleteCategory(int id) throws DataIntegrityViolationException,IllegalArgumentException{
		
		Category category = repo.findById(id).get();
		if(category!=null) {
			List<Equipment> equipments=equipmentRepo.findByCategory_CategoryId(id);
			
			for(Equipment equipment:equipments) {
				equipment.setCategory(null);
				equipment.setActive(false);
				equipmentRepo.save(equipment);
			}
			
			repo.deleteById(id);
			
			return new ResponseEntity<>(new CategoryDTO(category),HttpStatus.OK);
		}
		
		throw new IllegalArgumentException("Entity not found with specified details(ID).Either ID is not an integer or the user with specified ID does not exist.");
	}
	
	@Override
	public ResponseEntity<?> getAllcategory(){
		return ResponseEntity.ok(
				repo.findAll()
				.stream()
				.map(category->new CategoryDTO(category))
				.collect(Collectors.toList())
				);
	}
	
	@Override
	public ResponseEntity<?> getCategoryByUserId(int id) throws IllegalArgumentException{
		List<Category> categories=repo.findByUserId(id).get();
		
		if(categories != null) {
			return ResponseEntity.ok(
					categories
					.stream()
					.map(category->new CategoryDTO(category))
					.collect(Collectors.toList())
					);
		}
		throw new IllegalArgumentException("User id is null or not provided");
	}


	@Override
	public Category updateCategoryById(int id, CategoryDTO body) throws IllegalArgumentException{
		Category category=repo.findById(id).get();
		
		if(category != null) {
		category.setName(body.getName());
		category.setDescription(body.getDescription());
		return repo.save(category);
		}
		
		throw new IllegalArgumentException("Entity not found with specified details.Either ID is not an Integer or the user with specified ID does not exist.");
	}
	
}
