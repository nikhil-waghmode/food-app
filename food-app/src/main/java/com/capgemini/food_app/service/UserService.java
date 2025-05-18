package com.capgemini.food_app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.model.User;

public interface UserService {

	public User updateUser(Long id, String name, String email, String password, String phone, String location,
			String userType, MultipartFile profileImage) throws IOException;

	boolean deleteUser(Long id);

	List<User> getAllUsers();

	User getUserById(Long id);

	public User patchUser(Long id, String name, String email, String password, String phone, String location,
			String userType, MultipartFile profileImage) throws IOException;

	public User createUser(String name, String email, String password, String phone, String location, String userType,
			MultipartFile profileImage) throws IOException;

	User findByEmail(String email); // jwt

	public boolean existsByEmail(String email);

}