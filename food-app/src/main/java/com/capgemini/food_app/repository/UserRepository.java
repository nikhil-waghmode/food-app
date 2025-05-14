package com.capgemini.food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.food_app.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
