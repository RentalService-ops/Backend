package com.example.RentalService.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(new EquipmentDTO(savedEquipment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    /**
     * Returns all the Equipments.
     * */
    @GetMapping({"/getAllEquipments"})
	@PreAuthorize("hasRole('rental') or hasRole('admin') or hasRole('user')")
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
       return ResponseEntity.ok(equipmentService.getAllEquipments());
    }
    
    /**
     * @Param id: The id of the user whose equipment details are to be fetched.
     * Returns list of equipments associated with specific user.
     * */
    @GetMapping({"/getEquipmentByUserId"})
    @PreAuthorize("hasRole('rental') or hasRole('admin')")
    public ResponseEntity<?> getEquipMentByUserId(@RequestParam("id") int id) {
		return this.equipmentService.getEquipmentsByUserId(id);
    }
    
    /**
     * Updates equipment with given updated equipment details.
     * @Param body: The updated details of the equipment provided.
     * Returns the equipment object with updated details.
     * */
    @PatchMapping({"/editEquipment"})
    @PreAuthorize("hasRole('rental') or hasRole('admin')")
    public ResponseEntity<?> updateEquipment(@RequestPart("equipmentDTO") String equipmentDTOJson,
    										 @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {    	
    	 try {
             // Convert JSON string to Equipment object
             ObjectMapper objectMapper = new ObjectMapper();
             EquipmentDTO equipmentDTO = objectMapper.readValue(equipmentDTOJson, EquipmentDTO.class);

             Equipment savedEquipment = equipmentService.updateEquipment(equipmentDTO, imageFile);
             return ResponseEntity.status(HttpStatus.CREATED).body(new EquipmentDTO(savedEquipment));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
         }
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
    
    /**
     * Retrieves an image file by its filename.
     * @param filename Name of the image file.
     * @return Response entity containing the image resource or a not found response.
     * @throws MalformedURLException If the file path is incorrect.
     */
    @PreAuthorize("hasAnyRole('ROLE_user', 'ROLE_rental')")
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException {
        Path imagePath = Paths.get("D:\\java\\Project\\Backend\\src\\Image\\" + filename);
        Resource resource = new UrlResource(imagePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Get file extension
        String fileExtension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        
        // Determine media type based on extension
        MediaType mediaType;
        switch (fileExtension) {
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            case "bmp":
                mediaType = MediaType.parseMediaType("image/bmp");
                break;
            case "webp":
                mediaType = MediaType.parseMediaType("image/webp");
                break;
            case "jpg":
            case "jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            default:
                mediaType = MediaType.APPLICATION_OCTET_STREAM; // Fallback for unknown types
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

    
}
