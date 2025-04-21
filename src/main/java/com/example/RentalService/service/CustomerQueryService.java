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
	
	
	/**
	 * @return list of pending queries.
	 * */
	public List<CustomerQuery> getPendingQueries();

	/**
	 * Marks a query as resolved.
	 * @param id id of the query to be marked as resolved.
	 * @return true if query is present and resolved otherwise false.
	 * */
	public boolean resolveQuery(int id);

}
