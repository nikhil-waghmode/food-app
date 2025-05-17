package com.capgemini.food_app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.exception.UserNotFoundException;
import com.capgemini.food_app.exception.EmailAlreadyExistsException;

import com.capgemini.food_app.model.User;
import com.capgemini.food_app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(String name, String email, String password, String phone, String location, String userType,
			MultipartFile profileImage) throws IOException {
		// Check if the email already exists
		if (userRepository.existsByEmail(email)) {
			throw new EmailAlreadyExistsException("Email already exists");
		}

		// Define the directory to save uploaded user profile images
		String UPLOAD_DIR = "uploads/users/";

		// Create the directory if it doesn't exist
		Files.createDirectories(Paths.get(UPLOAD_DIR));

		String fileName = null;

		// Save profile image to disk if present
		if (profileImage != null && !profileImage.isEmpty()) {
			// Generate a unique file name
			fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR, fileName);

			// Save the file
			Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		}

		// Create and populate the User entity
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhone(phone);
		user.setLocation(location);
		user.setUserType(userType);
		user.setUserImg(fileName); // Save only the filename or full path if preferred

		// Save the user to the database
		return userRepository.save(user);
	}

	@Override
	public User updateUser(Long id, String name, String email, String password, String phone, String location,
			String userType, MultipartFile profileImage) throws IOException {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));

		// Check if email is being changed and if the new email already exists
		if (!existingUser.getEmail().equals(email) && userRepository.existsByEmail(email)) {
			throw new EmailAlreadyExistsException("Email already exists");
		}

		// If a new profile image is provided, save it
		if (profileImage != null && !profileImage.isEmpty()) {
			String UPLOAD_DIR = "uploads/users/";
			Files.createDirectories(Paths.get(UPLOAD_DIR));
			String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR, fileName);
			Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			existingUser.setUserImg(fileName); // Update with new image filename
		}

		// Update the rest of the fields
		existingUser.setName(name);
		existingUser.setEmail(email);
		existingUser.setPassword(password);
		existingUser.setPhone(phone);
		existingUser.setLocation(location);
		existingUser.setUserType(userType);

		return userRepository.save(existingUser);
	}

	@Override
	public boolean deleteUser(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User patchUser(Long id, String name, String email, String password, String phone, String location,
			String userType, MultipartFile profileImage) throws IOException {
		User present = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));

		// Handle email patch and uniqueness check
		if (email != null && !email.equals(present.getEmail())) {
			if (userRepository.existsByEmail(email)) {
				throw new EmailAlreadyExistsException("Email already exists");
			}
			present.setEmail(email);
		}

		// Patch other fields if provided
		if (name != null) {
			present.setName(name);
		}
		if (password != null) {
			present.setPassword(password);
		}
		if (phone != null) {
			present.setPhone(phone);
		}
		if (userType != null) {
			present.setUserType(userType);
		}
		if (location != null) {
			present.setLocation(location);
		}

		if (profileImage != null && !profileImage.isEmpty()) {
			String UPLOAD_DIR = "uploads/users/";
			Files.createDirectories(Paths.get(UPLOAD_DIR));
			String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR, fileName);
			Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			present.setUserImg(fileName);
		}

		return userRepository.save(present);
	}
}
