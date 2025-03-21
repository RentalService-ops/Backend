package com.example.RentalService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.RentalService.DTO.EquipmentDTO;
import com.example.RentalService.model.Equipment;
import com.example.RentalService.service.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller for handling Equipment Operations
 * */
@RestController
@RequestMapping("/api/equipment")
@CrossOrigin
public class EquipmentController {
	
	//Service for interacting with Equipment entity.
    @Autowired
    private EquipmentService equipmentService;

    /**
     * Adds the specified equipment to database.
     * @Param equipmentJson: Equipments details of the equipment
     * @Param imageFile: Image of the Equipment
     * returns Equipment details after equipment is stored in database successfully 
     * otherwise throw 500 Internal Server Error
     * */
    @PostMapping("/addEquipment")
    @PreAuthorize("hasRole('rental') or hasRole('admin')")
    public ResponseEntity<?> addEquipment(@RequestPart("equipment") String equipmentJson,
                                          @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            // Convert JSON string to Equipment object
            ObjectMapper objectMapper = new ObjectMapper();
            Equipment equipment = objectMapper.readValue(equipmentJson, Equipment.class);

            Equipment savedEquipment = equipmentService.addEquipment(equipment, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    /**
     * Returns all the Equipments.
     * */
    @GetMapping({"/getAllEquipments"})
	@PreAuthorize("hasRole('rental') or hasRole('admin') or hasRole('user')")
    public List<EquipmentDTO> getAllEquipments() {
       return this.equipmentService.getAllEquipments();
    }
    
    /**
     * @Param id: The id of the user whose equipment details are to be fetched.
     * Returns list of equipments associated with specific user.
     * */
    @GetMapping({"/getEquipmentByUserId"})
    @PreAuthorize("hasRole('rental') or hasRole('admin')")
    public List<EquipmentDTO> getEquipMentByUserId(@RequestParam("id") int id) {
       return this.equipmentService.getEquipmentsByUserId(id);
    }
    
    /**
     * Updates equipment with given updated equipment details.
     * @Param body: The updated details of the equipment provided.
     * Returns the equipment object with updated details.
     * */
    @PatchMapping({"/editEquipment"})
    @PreAuthorize("hasRole('rental') or hasRole('admin')")
    public Equipment updateEquipment(@RequestBody EquipmentDTO body) {
       return this.equipmentService.updateEquipment(body);
    }
    
    /**
     * Deletes the equipment with specified id.
     * @Param id: The id of the equipment to be deleted.
     * Returns success message after successfull deletion of equipment.
     * */
    @DeleteMapping({"/deleteEquipment/{id}"})
    @PreAuthorize("hasRole('rental') or hasRole('admin')")
    public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
       this.equipmentService.deleteEquipment(id);
       return ResponseEntity.ok("deleted Equipment");
    }
}
