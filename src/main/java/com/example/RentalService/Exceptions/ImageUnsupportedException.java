package com.example.RentalService.Exceptions;

import java.io.IOException;


public class ImageUnsupportedException extends IOException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageUnsupportedException(String message) {
		super(message);
	}
	
	public ImageUnsupportedException() {
		
	}
}
