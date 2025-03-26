package com.example.RentalService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.AddressDTO;
import com.example.RentalService.service.AddressService;

@RestController
@RequestMapping("/api/address/")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addAddress")
    @PreAuthorize("hasRole('admin') or hasRole('user') or hasRole('rental')")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO, @RequestParam int userId) {
        AddressDTO savedAddress = addressService.addAddress(addressDTO, userId);
        return ResponseEntity.ok(savedAddress);
    }

    @GetMapping("/getAddressesByUser/{userId}")
    @PreAuthorize("hasRole('admin') or hasRole('user') or hasRole('rental')")
    public ResponseEntity<List<AddressDTO>> getAddressesByUser(@PathVariable int userId) {
        List<AddressDTO> addresses = addressService.getAddressesByUser(userId);
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/updateAddress/{addressId}")
    @PreAuthorize("hasRole('admin') or hasRole('user') or hasRole('rental')")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable int addressId, @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/deleteAddress/{addressId}")
    @PreAuthorize("hasRole('admin') or hasRole('user') or hasRole('rental')")
    public ResponseEntity<String> deleteAddress(@PathVariable int addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok("Address deleted successfully.");
    }
}
