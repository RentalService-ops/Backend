package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RentalService.model.Rental_Bookings;


@Repository
public interface RentalBookingRepository extends JpaRepository<Rental_Bookings,Integer>{
	List<Rental_Bookings> findByUser_Id(int userId);
    List<Rental_Bookings> findByRenter_Id(int renterId); // Assuming 'renter' refers to the person renting the equipment

}
