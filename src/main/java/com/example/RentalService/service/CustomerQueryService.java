package com.example.RentalService.service;

import java.util.List;

import com.example.RentalService.model.CustomerQuery;

public interface CustomerQueryService {
	
	/**
	 * 
	 * Saves query received from user into the database.
	 * @param query the query details of the customer.
	 * */
	void saveQuery(CustomerQuery query);

	// Get only unresolved queries
	public List<CustomerQuery> getPendingQueries();

	// Mark a query as resolved
	public boolean resolveQuery(int id);

}
