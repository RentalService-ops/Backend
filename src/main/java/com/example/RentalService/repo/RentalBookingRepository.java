package com.example.RentalService.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.RentalService.model.Rental_Bookings;


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
    
    /**
     * Retrieves the total number of unique rentals based on rental_id.
     * This is used to support pagination when grouping booking statistics by renter.
     *
     * @return total number of distinct rental_id entries in rental_bookings
     */
    @Query(value = "SELECT COUNT(DISTINCT rental_id) FROM rental_bookings", nativeQuery = true)
    Long countDistinctRentalId();
    
    /**
     * Retrieves the total number of unique users based on user_id.
     * This is used to support pagination when grouping booking statistics by user.
     *
     * @return total number of distinct user_id entries in rental_bookings
     */
    @Query(value = "SELECT COUNT(DISTINCT user_id) FROM rental_bookings", nativeQuery = true)
    Long countDistinctUserId();
    
    /**
     * Retrieves the total number of unique equipment items based on equipment_id.
     * This is used to support pagination when grouping booking statistics by equipment.
     *
     * @return total number of distinct equipment_id entries in rental_bookings
     */
    @Query(value = "SELECT COUNT(DISTINCT equipment_id) FROM rental_bookings", nativeQuery = true)
    Long countDistinctEquipmentId();
    
    /**
     * Retrieves a list of rental bookings that have not been returned 
     * and have an end date before the specified date.
     *
     * This method is typically used to identify overdue rentals.
     *
     * @param date the cutoff date to check against the rental end dates
     * @return a list of {@link Rental_Bookings} that are overdue and not yet returned
     */
    List<Rental_Bookings> findByEndDateBeforeAndIsReturnedFalse(LocalDate date);
    
    /**
     * Deletes bookings based on userId
     * @param userId the id of user whose booking data is to be deleted.
     * */
    @Modifying //Annotation to add when using update , delete queries.
    void deleteRental_BookingsByUserId(int userId);
}
