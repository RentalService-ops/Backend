package com.example.RentalService.service;

import java.util.List;

import com.example.RentalService.DTO.AddressDTO;
import com.example.RentalService.Exceptions.UserNotFoundException;

public interface AddressService {

    /**
     * Adds a new address for a specific user.
     * 
     * @param addressDTO The address data transfer object containing the address details.
     * @param userId The ID of the user for whom the address is being added.
     * @throws IllegalArgumentException if user is not found.
     * @throws UserNotFoundException if user with specified id is not found.
     * @return The AddressDTO representing the added address.
     */
    AddressDTO addAddress(AddressDTO addressDTO, int userId) throws IllegalArgumentException,UserNotFoundException;

    /**
     * Retrieves a list of addresses for a specific user.
     * 
     * @param userId The ID of the user whose addresses are to be fetched.
     * @throws IllegalArgumentException if userId is null.
     * @return A list of AddressDTO objects representing the user's addresses.
     */
    List<AddressDTO> getAddressesByUser(int userId) throws IllegalArgumentException;

    /**
     * Updates the details of an existing address.
     * 
     * @param addressId The ID of the address to be updated.
     * @param addressDTO The new address details to update.
     * @throws IllegalArgumentException if the specified addressId is null.
     * @return The updated AddressDTO representing the updated address.
     */
    AddressDTO updateAddress(int addressId, AddressDTO addressDTO) throws IllegalArgumentException;

    /**
     * Deletes an address by its ID.
     * 
     * @param addressId The ID of the address to be deleted.
     * @throws IllegalArgumentException if the specified addressId is null.
     */
    void deleteAddress(int addressId) throws IllegalArgumentException;
}
