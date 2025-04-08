package com.example.RentalService.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RentalService.DTO.AddressDTO;
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.Address;
import com.example.RentalService.model.Users;
import com.example.RentalService.repo.UserRepository;
import com.example.RentalService.repo.addressRepo;
import com.example.RentalService.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private addressRepo addressRepository;

    @Autowired
    private UserRepository usersRepository;

    @Override
	public AddressDTO addAddress(AddressDTO addressDTO, int userId) throws IllegalArgumentException,UserNotFoundException{
        Users user = usersRepository.findById(userId)
                .get();
        
        if(user == null) {
        	throw new UserNotFoundException("User not found with specified id");
        }

        Address address = new Address(addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getState(),
                addressDTO.getZipCode(), addressDTO.getCountry(), user);

        Address savedAddress = addressRepository.save(address);
        return convertToDTO(savedAddress);
    }

    @Override
	public List<AddressDTO> getAddressesByUser(int userId)  throws IllegalArgumentException{
        List<Address> addresses = addressRepository.findByUserId(userId);
        if(addresses == null) {
        	throw new IllegalArgumentException("No address found with specified id.");
        }
        return addresses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
	public AddressDTO updateAddress(int addressId, AddressDTO addressDTO)  throws IllegalArgumentException{
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found. Either id is null or address do not exist with specified id."));

        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());

        Address updatedAddress = addressRepository.save(address);
        return convertToDTO(updatedAddress);
    }

    @Override
	public void deleteAddress(int addressId)  throws IllegalArgumentException{
        if (!addressRepository.existsById(addressId)) {
            throw new IllegalArgumentException("Address not found. Either id is null or address do not exist with specified id.");
        }
        addressRepository.deleteById(addressId);
    }

    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(address.getId(), address.getStreet(), address.getCity(),
                address.getState(), address.getZipCode(), address.getCountry());
    }
}
