package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.RentalService.model.Rental_Bookings;

/**
 * Repository interface for Rental_Bookings entity.
 * Provides methods to perform CRUD operations and custom queries 
 * for retrieving rental booking data based on different filters like 
 * user, renter, and equipment, including booking status aggregation.
 */
@Repository
public interface RentalBookingRepository extends JpaRepository<Rental_Bookings, Integer> {

    /**
     * Fetches all rental bookings created by a specific renter.
     *
     * @param rentalId the ID of the renter
     * @return list of bookings associated with the renter
     */
    List<Rental_Bookings> findByRenter_Id(int rentalId);

    /**
     * Fetches all rental bookings made by a specific user.
     *
     * @param userId the ID of the user
     * @return list of bookings made by the user
     */
    List<Rental_Bookings> findByUser_Id(int userId);

    /**
     * Counts the number of bookings made for equipment listed by a specific renter (by username).
     *
     * @param renterUsername the username of the equipment owner
     * @return number of bookings for that renter's equipment
     */
    long countByEquipment_User_Username(String renterUsername);

    /**
     * Retrieves aggregated booking statistics for each renter, including total and status-wise count.
     *
     * @param pageable pagination configuration
     * @return list of Object arrays containing:
     *         [rental_id, totalBookings, completedBookings, rejectedBookings, 
     *         cancelledBookings, approvedBookings, pendingBookings]
     */
    @Query(value = "SELECT rental_id, COUNT(rental_id),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.rental_id = rb.rental_id AND rb1.status = 'COMPLETED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.rental_id = rb.rental_id AND rb1.status = 'REJECTED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.rental_id = rb.rental_id AND rb1.status = 'CANCELLED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.rental_id = rb.rental_id AND rb1.status = 'APPROVED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.rental_id = rb.rental_id AND rb1.status = 'PENDING') "
            + "FROM rental_bookings rb GROUP BY rental_id", nativeQuery = true)
    List<Object[]> findBookingBySearchRenter(Pageable pageable);

    /**
     * Retrieves aggregated booking statistics for each user, including total and status-wise count.
     *
     * @param pageable pagination configuration
     * @return list of Object arrays containing:
     *         [user_id, totalBookings, completedBookings, rejectedBookings, 
     *         cancelledBookings, approvedBookings, pendingBookings]
     */
    @Query(value = "SELECT user_id, COUNT(user_id),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.user_id = rb.user_id AND rb1.status = 'COMPLETED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.user_id = rb.user_id AND rb1.status = 'REJECTED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.user_id = rb.user_id AND rb1.status = 'CANCELLED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.user_id = rb.user_id AND rb1.status = 'APPROVED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.user_id = rb.user_id AND rb1.status = 'PENDING') "
            + "FROM rental_bookings rb GROUP BY user_id", nativeQuery = true)
    List<Object[]> findBookingBySearchUser(Pageable pageable);

    /**
     * Retrieves aggregated booking statistics for each equipment item, including total and status-wise count.
     *
     * @param pageable pagination configuration
     * @return list of Object arrays containing:
     *         [equipment_id, totalBookings, completedBookings, rejectedBookings, 
     *         cancelledBookings, approvedBookings, pendingBookings]
     */
    @Query(value = "SELECT equipment_id, COUNT(equipment_id),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.equipment_id = rb.equipment_id AND rb1.status = 'COMPLETED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.equipment_id = rb.equipment_id AND rb1.status = 'REJECTED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.equipment_id = rb.equipment_id AND rb1.status = 'CANCELLED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.equipment_id = rb.equipment_id AND rb1.status = 'APPROVED'),"
            + "(SELECT COUNT(*) FROM rental_bookings rb1 WHERE rb1.equipment_id = rb.equipment_id AND rb1.status = 'PENDING') "
            + "FROM rental_bookings rb GROUP BY equipment_id", nativeQuery = true)
    List<Object[]> findBookingBySearchEquipment(Pageable pageable);
}
