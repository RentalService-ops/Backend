package com.example.RentalService.service;

import org.springframework.http.ResponseEntity;

import com.example.RentalService.DTO.CreatePaymentRequestDTO;
import com.example.RentalService.DTO.VerifyPaymentRequestDTO;
import com.razorpay.RazorpayException;

public interface PaymentService {

    /**
     * Creates a Razorpay payment order based on the provided request data.
     *
     * @param request the payment creation request containing booking ID, amount, and user ID
     * @return the generated Razorpay order ID
     * @throws RazorpayException if any error occurs while creating the order
     */
    String createOrder(CreatePaymentRequestDTO request) throws RazorpayException;

    /**
     * Verifies the payment signature returned from Razorpay to ensure authenticity.
     *
     * @param request the verification request containing order ID, payment ID, and signature
     * @return a message indicating whether the payment is verified or not
     */
    String verifyPayment(VerifyPaymentRequestDTO request);

    /**
     * Handles actions to be taken when a payment fails (e.g., updates status, logs failure).
     *
     * @param paymentOrderId the Razorpay order ID of the failed payment
     * @return a message indicating failure handling result
     */
    String handleFailedPayment(String paymentOrderId);

    /**
     * Handles actions when a payment is rejected (e.g., due to booking being declined).
     *
     * @param paymentOrderId the Razorpay order ID of the rejected payment
     * @return a message indicating rejection handling result
     */
    String handleRejectedPayment(String paymentOrderId);

    /**
     * Retrieves all payment records associated with a specific user ID.
     *
     * @param id the user ID
     * @return a ResponseEntity containing payment details or an appropriate error message
     */
    ResponseEntity<?> getPaymentByUserId(int id);
    
    ResponseEntity<?> getAllPaymentOfRental(int id);
    
    /**
     * Deletes payment details associated with user with given id
     * @param userId id of the user whose payment details are to be deleted.
     * */
    void deletePaymentByUserId(int userId);
}
