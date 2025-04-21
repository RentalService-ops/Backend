package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.RentalService.model.Rental_Bookings;


@Repository
public interface RentalBookingRepository extends JpaRepository<Rental_Bookings,Integer>{
	List<Rental_Bookings> findByRenter_Id(int rentalId);  
	List<Rental_Bookings> findByUser_Id(int userId);
	long countByEquipment_User_Username(String renterUsername);
	
	@Query(value="select rental_id, count(rental_id)\r\n"
			+ ",(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.rental_id=rb.rental_id\r\n"
			+ " group by status having status=\"COMPLETED\"\r\n"
			+ ") as completedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.rental_id=rb.rental_id\r\n"
			+ " group by status having status=\"REJECTED\"\r\n"
			+ ") as rejectedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.rental_id=rb.rental_id\r\n"
			+ " group by status having status=\"CANCELLED\"\r\n"
			+ ") as cancelledBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.rental_id=rb.rental_id\r\n"
			+ " group by status having status=\"APPROVED\"\r\n"
			+ ") as approvedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.rental_id=rb.rental_id\r\n"
			+ " group by status having status=\"PENDING\"\r\n"
			+ ") as pendingBookings from rental_bookings rb group by rental_id",nativeQuery=true)
	List<Object[]> findBookingBySearchRenter(Pageable pageable);
	
	@Query(value="select user_id,count(user_id)\r\n"
			+ ",(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.user_id=rb.user_id\r\n"
			+ " group by status having status=\"COMPLETED\"\r\n"
			+ ") as completedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.user_id=rb.user_id\r\n"
			+ " group by status having status=\"REJECTED\"\r\n"
			+ ") as rejectedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.user_id=rb.user_id\r\n"
			+ " group by status having status=\"CANCELLED\"\r\n"
			+ ") as cancelledBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.user_id=rb.user_id\r\n"
			+ " group by status having status=\"APPROVED\"\r\n"
			+ ") as approvedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.user_id=rb.user_id\r\n"
			+ " group by status having status=\"PENDING\"\r\n"
			+ ") as pendingBookings from rental_bookings rb group by user_id",nativeQuery=true)
	List<Object[]> findBookingBySearchUser(Pageable pageable);
	
	@Query(value="select equipment_id,count(equipment_id)\r\n"
			+ ",(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.equipment_id=rb.equipment_id\r\n"
			+ " group by status having status=\"COMPLETED\"\r\n"
			+ ") as completedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.equipment_id=rb.equipment_id\r\n"
			+ " group by status having status=\"REJECTED\"\r\n"
			+ ") as rejectedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.equipment_id=rb.equipment_id\r\n"
			+ " group by status having status=\"CANCELLED\"\r\n"
			+ ") as cancelledBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.equipment_id=rb.equipment_id\r\n"
			+ " group by status having status=\"APPROVED\"\r\n"
			+ ") as approvedBookings,\r\n"
			+ "(select count(*) from rental_bookings rb1\r\n"
			+ " where rb1.equipment_id=rb.equipment_id\r\n"
			+ " group by status having status=\"PENDING\"\r\n"
			+ ") as pendingBookings from rental_bookings rb group by equipment_id",nativeQuery=true)
	List<Object[]> findBookingBySearchEquipment(Pageable pageable);
}
