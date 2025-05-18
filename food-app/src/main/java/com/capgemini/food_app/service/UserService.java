package com.capgemini.food_app.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.model.User;

public interface UserService {
	public User createUser(String name, String email, String password, String phone, String location, String userType, MultipartFile profileImage) throws IOException;
	
	public User createUser1(User user) throws IOException;

	public User updateUser(Long id, String name, String email, String password, String phone, String location, String userType, MultipartFile profileImage) throws IOException;
	
	boolean deleteUser(Long id);
	
	List<User> getAllUsers();
	
	User getUserById(Long id);
	
//	User patchUser(Long id,User user);
	public User patchUser(Long id, String name, String email, String password, String phone, String location, String userType, MultipartFile profileImage) throws IOException;

	boolean existsByEmail(String email);

	User findByEmail(String email); // jwt

}
