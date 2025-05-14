package com.capgemini.food_app.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.food_app.model.User;

public interface UserService {
	User createUser(User user);
	
	User updateUser(Long id, User user);
	
	boolean deleteUser(Long id);
	
	List<User> getAllUsers();
	
	User getUserById(Long id);
	
	User patchUser(Long id,User user);
}
