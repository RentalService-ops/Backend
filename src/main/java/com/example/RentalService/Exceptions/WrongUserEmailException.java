package com.example.RentalService.Exceptions;

@SuppressWarnings("serial")
public class WrongUserEmailException extends RuntimeException{
	public WrongUserEmailException(String message) {
		super(message);
	}
	
	public WrongUserEmailException() {
		
	}
}
