package com.example.RentalService.DTO;

import lombok.Data;

@Data
public class VerifyPaymentRequestDTO {
    private String orderId;
    private String paymentId;
    private String signature;
}