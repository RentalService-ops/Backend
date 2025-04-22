package com.example.RentalService.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//PaymentID

    @Column(nullable = false)
    private String razorpayOrderId;// Razorpay order id

    @Column(nullable = true)
    private String razorpayPaymentId;// Razorpay payment id

    @Column(nullable = true)
    private String razorpaySignature;//Razorpay signature for payment verification

    @Column(nullable = false)
    private Double amount;//Payment amount

    @Column(nullable = false)
    private String currency;//Currency in which transaction is done

    @Column(nullable = false)
    private String status;//Payment status 

    @Column(nullable = false)
    private LocalDate paymentDate;//Date of payment

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;//User associated with payment

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Rental_Bookings order;//Order associated with payment
}
