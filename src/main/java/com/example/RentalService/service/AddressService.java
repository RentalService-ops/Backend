package com.example.RentalService.service;

import java.util.List;

import com.example.RentalService.DTO.AddressDTO;

public interface AddressService {

    /**
     * Adds a new address for a specific user.
     * 
     * @param addressDTO The address data transfer object containing the address details.
     * @param userId The ID of the user for whom the address is being added.
     * @return The AddressDTO representing the added address.
     */
    AddressDTO addAddress(AddressDTO addressDTO, int userId);

    /**
     * Retrieves a list of addresses for a specific user.
     * 
     * @param userId The ID of the user whose addresses are to be fetched.
     * @return A list of AddressDTO objects representing the user's addresses.
     */
    List<AddressDTO> getAddressesByUser(int userId);

    /**
     * Updates the details of an existing address.
     * 
     * @param addressId The ID of the address to be updated.
     * @param addressDTO The new address details to update.
     * @return The updated AddressDTO representing the updated address.
     */
    AddressDTO updateAddress(int addressId, AddressDTO addressDTO);

    /**
     * Deletes an address by its ID.
     * 
     * @param addressId The ID of the address to be deleted.
     */
    void deleteAddress(int addressId);
}
