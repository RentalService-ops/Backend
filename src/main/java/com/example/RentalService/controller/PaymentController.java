package com.example.RentalService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RentalService.DTO.CreatePaymentRequestDTO;
import com.example.RentalService.DTO.VerifyPaymentRequestDTO;
import com.example.RentalService.service.PaymentService;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasAnyRole('user', 'rental')")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Creates a new Razorpay order for a booking.
     *
     * @param request the payment creation request containing booking and amount details
     * @return the Razorpay order ID or an error message if creation fails
     */
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody CreatePaymentRequestDTO request) {
        try {
            String order = paymentService.createOrder(request);
            return ResponseEntity.ok(order);
        } catch (RazorpayException e) {
            return ResponseEntity.status(500).body("Error creating order: " + e.getMessage());
        }
    }

    /**
     * Verifies the payment using order ID, payment ID, and signature.
     *
     * @param request the verification request from Razorpay
     * @return result message indicating success or failure
     */
    @PostMapping("/verify-payment")
    public ResponseEntity<String> verifyPayment(@RequestBody VerifyPaymentRequestDTO request) {
        String result = paymentService.verifyPayment(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Handles a failed payment and updates the status accordingly.
     *
     * @param paymentId the Razorpay order ID of the failed payment
     * @return confirmation message after handling failure
     */
    @PostMapping("/failed-payment/{paymentId}")
    public ResponseEntity<String> handleFailedPayment(@PathVariable("paymentId") String paymentId){
        return ResponseEntity.ok(paymentService.handleFailedPayment(paymentId));
    }

    /**
     * Handles a rejected payment (e.g., booking declined).
     *
     * @param paymentId the Razorpay order ID of the rejected payment
     * @return confirmation message after handling rejection
     */
    @PostMapping("/reject-payment/{paymentId}")
    public ResponseEntity<String> handleRejectPayment(@PathVariable("paymentId") String paymentId){
        return ResponseEntity.ok(paymentService.handleRejectedPayment(paymentId));
    }

    /**
     * Fetches all payment records for a specific user.
     *
     * @param id the user ID
     * @return a list of payment records associated with the user
     */
    @GetMapping("/getPayment/{id}")
    public ResponseEntity<?> getPayment(@PathVariable int id){
        return paymentService.getPaymentByUserId(id);
    }
    
    /**
     * @param id the id of the renter whose payment details are to be fetched
     * @return ResponseEntity object with details related to payments associated with specific renter
     * */
    @GetMapping("getRentalPayment/{id}")
    public ResponseEntity<?> getRentalPayments(@PathVariable int id){
    	return paymentService.getAllPaymentOfRental(id);
    }
}
