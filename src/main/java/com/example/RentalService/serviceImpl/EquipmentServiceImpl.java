package com.example.RentalService.serviceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.example.RentalService.DTO.EquipmentDTO;
import com.example.RentalService.Exceptions.ImageUnsupportedException;
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Equipment;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.EquipmentRepo;
import com.example.RentalService.repo.RentalBookingRepository;
import com.example.RentalService.service.AuthService;
import com.example.RentalService.service.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService{

    @Autowired
    private EquipmentRepo equipmentRepo;
    
    @Autowired
    private AuthService userService;
    
    @Autowired
    RentalBookingRepository rentalRepo;

    private static final String IMAGE_DIRECTORY = "D:\\java\\Project\\Backend\\src\\Image";

    @Override
	public Equipment addEquipment(Equipment equipment, MultipartFile imageFile) throws ImageUnsupportedException,MaxUploadSizeExceededException,UserNotFoundException{
        
	    if (equipment.getUser() == null || equipment.getUser().getId() == 0) {
	        throw new IllegalArgumentException("User ID is required but was null or 0.");
	    }

	    // Fetch the user from DB
	    Users user = userService.findUsreById(equipment.getUser().getId());

	    if (user == null) {
	        throw new UserNotFoundException("User with ID " + equipment.getUser().getId() + " not found.");
	    }

	    equipment.setUser(user); // Assign the fetched user
	    equipment.setActive(true);
    	
    	if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = storeImage(imageFile);
            equipment.setImageUrl(fileName);
        }

        return equipmentRepo.save(equipment);
    }

    private String storeImage(MultipartFile file) throws ImageUnsupportedException{
        // Ensure directory exists
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique filename
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(IMAGE_DIRECTORY, fileName);

        // Save file to disk
        Path path=null;
        
        try {
        	path=Files.write(filePath, file.getBytes());
        }
        catch(Exception e) {}
        
        if(path !=null) {
            return fileName;
        }
        
        throw new ImageUnsupportedException();
    }
    
    @Override
	public List<EquipmentDTO> getAllEquipments() {
        List<EquipmentDTO> equipments = new ArrayList<>();
        List<Equipment> equipmentsObtained = equipmentRepo.findAll();

        for(Equipment equipment:equipmentsObtained) {
           	if(equipment.isActive()) {
                EquipmentDTO equipmentDTO = new EquipmentDTO(equipment);
				equipments.add(equipmentDTO);
			}
		}

        return equipments;
     }

     @Override
	public ResponseEntity<?> getEquipmentsByUserId(int id) throws IllegalArgumentException{
        List<EquipmentDTO> equipments = new ArrayList<>();
        List<Equipment> equipmentsObtained = this.equipmentRepo.findByUserId(id);
        
        if(equipmentsObtained!=null) {
			for(Equipment equipment : equipmentsObtained) {
			   if(equipment.isActive() && equipment.getCategory()!=null) {
				EquipmentDTO equipmentDTO = new EquipmentDTO(equipment);
				equipments.add(equipmentDTO);
			}
			}
	        return ResponseEntity.ok(equipments);
		}
        throw new IllegalArgumentException("Credentials provided are not valid or missing.");
     }

     @Override
	 public Equipment updateEquipment(EquipmentDTO updatedDetails, MultipartFile imageFile) throws ImageUnsupportedException,MaxUploadSizeExceededException,IllegalArgumentException {
    	
        Equipment equipment = this.equipmentRepo.findByEquipmentId(updatedDetails.getEquipmentId());
        if(equipment != null) {
		equipment.setQuantity(updatedDetails.getQuantity());
        equipment.setPricePerDay(updatedDetails.getPricePerDay());
        equipment.setDescription(updatedDetails.getDescription());
        equipment.setName(updatedDetails.getName());
        
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = storeImage(imageFile);
            equipment.setImageUrl(fileName);
        }
        return this.equipmentRepo.save(equipment);
        }
        
        throw new IllegalArgumentException("Specified equipment ID is not valid.");
     }

     @Override
	 public void deleteEquipment(int id) throws IllegalArgumentException{
    	 Equipment equipment=this.equipmentRepo.findById(id).get();
    	 if(equipment == null) {
    		 throw new IllegalArgumentException("Id is null or equipment with given id does not exist.");
    	 }
    	 equipment.setActive(false);  
    	 equipmentRepo.save(equipment);
     }
     
 	@Override
 	public Page<Equipment> searchEquipmentByName(String name, Pageable pageable) {
 		return equipmentRepo.findByNameContainingIgnoreCase(name, pageable);

 	}

 	@Override
 	public Page<Equipment> getAllEquipment(Pageable pageable) {
 		return equipmentRepo.findAll(pageable);

 	}

 	@Override
 	public Equipment getEquipmentById(int id) {
 		return equipmentRepo.findById(id).orElse(null);
 	}
}
