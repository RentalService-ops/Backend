package com.example.RentalService.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.RentalService.DTO.EquipmentDTO;
import com.example.RentalService.model.Equipment;
import com.example.RentalService.repo.EquipmentRepo;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepo equipmentRepo;

    private static final String IMAGE_DIRECTORY = "C:\\Users\\700048\\Desktop\\Backend\\src\\Image";

    public Equipment addEquipment(Equipment equipment, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = storeImage(imageFile);
            equipment.setImageUrl(fileName);
        }

        return equipmentRepo.save(equipment);
    }

    private String storeImage(MultipartFile file) throws IOException {
        // Ensure directory exists
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique filename
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(IMAGE_DIRECTORY, fileName);

        // Save file to disk
        Files.write(filePath, file.getBytes());

        return fileName;
    }
    public List<EquipmentDTO> getAllEquipments() {
        List<EquipmentDTO> equipments = new ArrayList<>();
        List<Equipment> equipmentsObtained = equipmentRepo.findAll();

        for(Equipment equipment:equipmentsObtained) {
           EquipmentDTO equipmentDTO = new EquipmentDTO(equipment);
           equipments.add(equipmentDTO);
        }

        return equipments;
     }

     public List<EquipmentDTO> getEquipmentsByUserId(int id) {
        List<EquipmentDTO> equipments = new ArrayList<>();
        List<Equipment> equipmentsObtained = this.equipmentRepo.findByUserId(id);

        for(Equipment equipment : equipmentsObtained) {
           EquipmentDTO equipmentDTO = new EquipmentDTO(equipment);
           equipments.add(equipmentDTO);
        }

        return equipments;
     }

     public Equipment updateEquipment(EquipmentDTO updatedDetails) {
        Equipment equipment = this.equipmentRepo.findByEquipmentId(updatedDetails.getEquipmentId());
        equipment.setQuantity(updatedDetails.getQuantity());
        equipment.setPricePerDay(updatedDetails.getPricePerDay());
        equipment.setDescription(updatedDetails.getDescription());
        equipment.setName(updatedDetails.getName());
        return this.equipmentRepo.save(equipment);
     }

     public void deleteEquipment(int id) {
        this.equipmentRepo.deleteById(id);
     }
}
