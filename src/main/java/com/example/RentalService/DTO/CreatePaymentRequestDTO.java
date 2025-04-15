package com.example.RentalService.DTO;

import lombok.Data;

@Data
public class CreatePaymentRequestDTO {
    private int userId;
    private int bookingId;
    private Integer amount;
}