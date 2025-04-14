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
	
	// Get all queries
	public List<CustomerQuery> getAllQueries();

	// Get only unresolved queries
	public List<CustomerQuery> getPendingQueries();

	// Mark a query as resolved
	public boolean resolveQuery(int id);

	// Mark a query as not resolved (change back to pending)
	public boolean markQueryAsNotResolved(int id);
}
