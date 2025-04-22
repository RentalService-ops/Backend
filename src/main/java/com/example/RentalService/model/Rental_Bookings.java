package com.example.RentalService.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Rental_Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;//Booking ID

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;//User associated with booking

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rental_id", nullable = false)
    private Users renter;//Renter associated with booking

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;//Equipment associated with booking

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;//Address associated with booking

    @Column(nullable = false)
    private int equipmentQuantity;//Equipment Quantity associated with booking

    @Column(nullable = false)
    private LocalDate startDate;//Booking start date.

    @Column(nullable = false)
    private LocalDate endDate;//Booking end date.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;//Booking status

    @Column(name = "total_price")
    private BigDecimal totalPrice;//Total price for renting equipment whose booking is made.
    
    @Column(name="isReturned")
    @Builder.Default    //For default property value initialization.
    private boolean isReturned=false;//Determines whether equipment lot associated with the booking is returned or not.
}
