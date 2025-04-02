package com.example.RentalService.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.RentalService.DTO.EquipmentDTO;
import com.example.RentalService.model.Equipment;

public interface EquipmentService {

    /**
     * Add a new equipment along with an image.
     * 
     * @param equipment the equipment details to be added
     * @param imageFile the image file associated with the equipment
     * @return the saved equipment
     * @throws IOException if an error occurs during the image storage process
     */
    Equipment addEquipment(Equipment equipment, MultipartFile imageFile) throws IOException;

    /**
     * Get a list of all available equipment.
     * 
     * @return list of EquipmentDTO objects representing all the equipment
     */
    List<EquipmentDTO> getAllEquipments();

    /**
     * Get a list of equipment by a user's ID.
     * 
     * @param id the user ID to filter the equipment by
     * @return ResponseEntity containing the list of equipment or an error message
     */
    ResponseEntity<?> getEquipmentsByUserId(int id);

    /**
     * Update equipment details based on the provided DTO.
     * 
     * @param updatedDetails the EquipmentDTO with updated information
     * @return the updated Equipment object
     */
    Equipment updateEquipment(EquipmentDTO updatedDetails);

    /**
     * Delete an equipment by its ID.
     * 
     * @param id the ID of the equipment to be deleted
     */
    void deleteEquipment(int id);
}
