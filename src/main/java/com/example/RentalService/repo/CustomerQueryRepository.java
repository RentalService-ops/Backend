package com.example.RentalService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RentalService.model.CustomerQuery;

@Repository
public interface CustomerQueryRepository extends JpaRepository<CustomerQuery,Integer>{
	List<CustomerQuery> findByQueryStatus(String queryStatus);
}
