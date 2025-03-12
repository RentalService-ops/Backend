package com.example.RentalService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Customer_query")
public class CustomerQuery {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int query_id;
	
	private String username;
	
	private String useremail;
	
	private String query;
	
	private String queryStatus;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private Users user;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}
	
	public int getUserId() {
		return this.user.getId();
	}
	
	public int getId() {
		return this.query_id;
	}
	
	public void setId(int id) {
		this.query_id=id;
	}
	public void setUser(Users user) {
		this.user=user;
	}
}
