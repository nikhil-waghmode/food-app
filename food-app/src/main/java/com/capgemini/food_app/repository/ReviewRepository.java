package com.capgemini.food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.food_app.model.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews,Long> {
	 

}
