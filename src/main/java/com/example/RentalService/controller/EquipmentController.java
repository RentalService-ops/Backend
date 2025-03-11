package com.example.RentalService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


@RestController
@RequestMapping("/api/equipment")
@CrossOrigin
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

//    @PostMapping("/addEquipment")
//    public ResponseEntity<?> addEquipment(@RequestPart("equipment") Equipment equipment,
//                                          @RequestPart("imageFile") MultipartFile imageFile) {
//        try {
//            Equipment savedEquipment = equipmentService.addEquipment(equipment, imageFile);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipment);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }
    
    @PostMapping("/addEquipment")
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
    
    @GetMapping({"/getAllEquipments"})
    public List<EquipmentDTO> getAllEquipments() {
       return this.equipmentService.getAllEquipments();
    }

    @GetMapping({"/getEquipmentByUserId"})
    public List<EquipmentDTO> getEquipMentByUserId(@RequestParam("id") int id) {
       return this.equipmentService.getEquipmentsByUserId(id);
    }

    @PatchMapping({"/editEquipment"})
    public Equipment updateEquipment(@RequestBody EquipmentDTO body) {
       return this.equipmentService.updateEquipment(body);
    }

    @DeleteMapping({"/deleteEquipment/{id}"})
    public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
       this.equipmentService.deleteEquipment(id);
       return ResponseEntity.ok("deleted Equipment");
    }
}
