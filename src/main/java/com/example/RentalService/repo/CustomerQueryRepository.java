package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RentalService.model.CustomerQuery;


@Repository
public interface CustomerQueryRepository extends JpaRepository<CustomerQuery, Integer> {

    /**
     * Retrieves all customer queries with the specified query status.
     *
     * @param queryStatus the status of the customer query (e.g., "Pending", "Resolved")
     * @return a list of customer queries matching the given status
     */
    List<CustomerQuery> findByQueryStatus(String queryStatus);
}
