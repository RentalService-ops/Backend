package com.example.RentalService.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.example.RentalService.DTO.EquipmentDTO;
import com.example.RentalService.Exceptions.ImageUnsupportedException;
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Equipment;

import jakarta.persistence.EntityNotFoundException;

public interface EquipmentService {

    /**
     * Add a new equipment along with an image.
     * 
     * @param equipment the equipment details to be added
     * @param imageFile the image file associated with the equipment
     * @return the saved equipment
     * @throws ImageUnsupportedException if IO operation is not successfull while storing image.
     * @throws MaxUploadFileSizeException if image file is too large to store
     * @throws UserNotFoundException if user does not exist with specified id. 
     */
    Equipment addEquipment(Equipment equipment, MultipartFile imageFile) throws ImageUnsupportedException,MaxUploadSizeExceededException,UserNotFoundException;

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
     * @throws IllegalArgumentException in case id is null.
     * @return ResponseEntity containing the list of equipment or an error message
     */
    ResponseEntity<?> getEquipmentsByUserId(int id);

    /**
     * Update equipment details based on the provided DTO and image.
     * 
     * @param updatedDetails the EquipmentDTO with updated information
     * @param imageFile the new image of the equipment uploaded.
     * @return the updated Equipment object
     * @throws ImageUnsupportedException if IO operation is not successfull while storing image.
     * @throws MaxUploadFileSizeException if image file is too large to store 
     * @throws IllegalArgumentException if id specified in updated equipment details is null.
     */
    Equipment updateEquipment(EquipmentDTO updatedDetails, MultipartFile imageFile) throws ImageUnsupportedException,MaxUploadSizeExceededException,IllegalArgumentException;

    /**
     * Delete an equipment by its ID.
     * 
     * @param id the ID of the equipment to be deleted
     * @throws IllegalArgumentException if id is null.
     */
    void deleteEquipment(int id) throws IllegalArgumentException;
    
    /**
     * Searches for equipment entities whose names match the given search term.
     *
     * @param search   the name or partial name to search for
     * @param pageable the pagination and sorting information
     * @return a paginated list of equipment matching the search term
     * @throws IllegalArgumentException if the search term is null or pageable is invalid
     */
    Page<Equipment> searchEquipmentByName(String search, Pageable pageable);

    /**
     * Retrieves all equipment entities with pagination support.
     *
     * @param pageable the pagination and sorting information
     * @return a paginated list of all equipment
     * @throws IllegalArgumentException if the pageable parameter is invalid
     */
    Page<Equipment> getAllEquipment(Pageable pageable);

    /**
     * Retrieves a single equipment entity by its unique identifier.
     *
     * @param id the ID of the equipment to retrieve
     * @return the equipment entity with the specified ID
     * @throws EntityNotFoundException if no equipment is found with the given ID
     */
    Equipment getEquipmentById(int id);
}

