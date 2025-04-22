package com.example.RentalService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Customer_query")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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
}
