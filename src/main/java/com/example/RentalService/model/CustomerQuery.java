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

    // Property to store the unique query ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int query_id;

    // Property to store the username of the user who made the query
    private String username;

    // Property to store the email of the user who made the query
    private String useremail;

    // Property to store the actual query submitted by the user
    private String query;

    // Property to store the status of the query (e.g., pending, resolved)
    private String queryStatus;

    // Property to store the associated user for this query
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private Users user;

    // Getter for username
    /**
     * Gets the username of the user who made the query.
     * 
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    // Setter for username
    /**
     * Sets the username of the user who made the query.
     * 
     * @param username the username to be set for the query.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for useremail
    /**
     * Gets the email address of the user who made the query.
     * 
     * @return the email of the user.
     */
    public String getUseremail() {
        return useremail;
    }

    // Setter for useremail
    /**
     * Sets the email address of the user who made the query.
     * 
     * @param useremail the email to be set for the query.
     */
    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    // Getter for query
    /**
     * Gets the actual query submitted by the user.
     * 
     * @return the query text submitted by the user.
     */
    public String getQuery() {
        return query;
    }

    // Setter for query
    /**
     * Sets the actual query text submitted by the user.
     * 
     * @param query the query to be set for the user.
     */
    public void setQuery(String query) {
        this.query = query;
    }

    // Getter for queryStatus
    /**
     * Gets the status of the query (e.g., pending, resolved).
     * 
     * @return the current status of the query.
     */
    public String getQueryStatus() {
        return queryStatus;
    }

    // Setter for queryStatus
    /**
     * Sets the status of the query.
     * 
     * @param queryStatus the status to be set for the query.
     */
    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

    // Getter for userId
    /**
     * Gets the ID of the user who submitted the query.
     * 
     * @return the user ID associated with this query.
     */
    public int getUserId() {
        return this.user.getId();
    }

    // Getter for query_id
    /**
     * Gets the unique ID of the query.
     * 
     * @return the query ID.
     */
    public int getId() {
        return this.query_id;
    }

    // Setter for query_id
    /**
     * Sets the unique ID for the query.
     * 
     * @param id the ID to be set for the query.
     */
    public void setId(int id) {
        this.query_id = id;
    }

    // Setter for user
    /**
     * Sets the user who submitted the query.
     * 
     * @param user the user to be associated with this query.
     */
    public void setUser(Users user) {
        this.user = user;
    }
}
