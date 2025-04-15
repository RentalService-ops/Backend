package com.example.RentalService.service;

import com.example.RentalService.DTO.CreatePaymentRequestDTO;
import com.example.RentalService.DTO.VerifyPaymentRequestDTO;
import com.razorpay.RazorpayException;

public interface PaymentService {
    String createOrder(CreatePaymentRequestDTO request) throws RazorpayException;
    String verifyPayment(VerifyPaymentRequestDTO request);
}