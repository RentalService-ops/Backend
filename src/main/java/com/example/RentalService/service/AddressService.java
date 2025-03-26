package com.example.RentalService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RentalService.DTO.AddressDTO;
import com.example.RentalService.model.Address;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.UserRepository;
import com.example.RentalService.repo.addressRepo;

@Service
public class AddressService {

    @Autowired
    private addressRepo addressRepository;

    @Autowired
    private UserRepository usersRepository;

    public AddressDTO addAddress(AddressDTO addressDTO, int userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = new Address(addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getState(),
                addressDTO.getZipCode(), addressDTO.getCountry(), user);

        Address savedAddress = addressRepository.save(address);
        return convertToDTO(savedAddress);
    }

    public List<AddressDTO> getAddressesByUser(int userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AddressDTO updateAddress(int addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());

        Address updatedAddress = addressRepository.save(address);
        return convertToDTO(updatedAddress);
    }

    public void deleteAddress(int addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new RuntimeException("Address not found");
        }
        addressRepository.deleteById(addressId);
    }

    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(address.getId(), address.getStreet(), address.getCity(),
                address.getState(), address.getZipCode(), address.getCountry());
    }
}
