package com.example.RentalService.DTO;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
	private BigInteger sentOTP;
	private String resetPassword;
	private String email;
}
