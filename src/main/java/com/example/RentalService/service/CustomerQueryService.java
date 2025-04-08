package com.example.RentalService.service;

import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.model.CustomerQuery;

public interface CustomerQueryService {
	
	/**
	 * 
	 * Saves query received from user into the database.
	 * @param query the query details of the customer.
	 * */
	void saveQuery(CustomerQuery query) throws UserNotFoundException;
}
