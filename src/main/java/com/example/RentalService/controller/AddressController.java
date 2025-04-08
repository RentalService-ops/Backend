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
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.service.AddressService;

@RestController
@RequestMapping("/api/address/")
public class AddressController {

    // AddressService instance to interact with business logic layer
    @Autowired
    private AddressService addressService;

    /**
     * Adds a new address for the user.
     * @param addressDTO The address details to be added.
     * @param userId The ID of the user for whom the address is to be added.
     * @throws IllegalArgumentException if user is not found.
     * @throws UserNotFoundException if user with specified id is not found.
     * @return A ResponseEntity containing the saved address details.
     */
    @PostMapping("/addAddress")
    @PreAuthorize("hasRole('admin') or hasRole('user') or hasRole('rental')")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO, @RequestParam int userId) throws IllegalArgumentException,UserNotFoundException {
        // Calling the service layer to save the address
        AddressDTO savedAddress = addressService.addAddress(addressDTO, userId);
        return ResponseEntity.ok(savedAddress); // Returning the saved address details
    }

    /**
     * Retrieves all addresses associated with a specific user.
     * @param userId The ID of the user whose addresses are to be fetched.
     * @throws IllegalArgumentException if userId is null.
     * @return A ResponseEntity containing a list of address details for the user.
     */
    @GetMapping("/getAddressesByUser/{userId}")
    @PreAuthorize("hasRole('admin') or hasRole('user') or hasRole('rental')")
    public ResponseEntity<List<AddressDTO>> getAddressesByUser(@PathVariable int userId) throws IllegalArgumentException{
        // Fetching the list of addresses associated with the provided user ID
        List<AddressDTO> addresses = addressService.getAddressesByUser(userId);
        return ResponseEntity.ok(addresses); // Returning the list of addresses
    }

    /**
     * Updates an existing address based on the provided address ID.
     * @param addressId The ID of the address to be updated.
     * @param addressDTO The updated address details.
     * @throws IllegalArgumentException if the specified addressId is null.
     * @return A ResponseEntity containing the updated address details.
     */
    @PutMapping("/updateAddress/{addressId}")
    @PreAuthorize("hasRole('admin') or hasRole('user') or hasRole('rental')")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable int addressId, @RequestBody AddressDTO addressDTO) throws IllegalArgumentException{
        // Calling the service layer to update the address based on the provided address ID
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress); // Returning the updated address details
    }

    /**
     * Deletes an address based on the provided address ID.
     * @param addressId The ID of the address to be deleted.
     * @throws IllegalArgumentException if the specified addressId is null
     * @return A ResponseEntity containing a success message.
     */
    @DeleteMapping("/deleteAddress/{addressId}")
    @PreAuthorize("hasRole('admin') or hasRole('user') or hasRole('rental')")
    public ResponseEntity<String> deleteAddress(@PathVariable int addressId) throws IllegalArgumentException{
        // Calling the service layer to delete the address based on the provided address ID
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok("Address deleted successfully."); // Returning a success message
    }
}
