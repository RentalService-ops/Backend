package com.example.RentalService.service;

import com.example.RentalService.model.CustomerQuery;

public interface CustomerQueryService {
	
	/**
	 * 
	 * Saves query received from user into the database.
	 * */
	void saveQuery(CustomerQuery query);
}
