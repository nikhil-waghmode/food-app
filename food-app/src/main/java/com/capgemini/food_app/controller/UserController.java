package com.capgemini.food_app.controller;

import java.util.List;

import com.capgemini.food_app.model.User;
import com.capgemini.food_app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("Fetching all users");
		List<User> users = userService.getAllUsers();
		log.debug("Number of users found: {}", users.size());
		return ResponseEntity.ok(users);
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		log.info("Creating new user with email: {}", user.getEmail());
		User saved = userService.createUser(user);
		log.debug("Created user: {}", saved);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		log.info("Fetching user with ID: {}", id);
		User user = userService.getUserById(id);
		if (user == null) {
			log.warn("User with ID {} not found", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		log.info("Deleting user with ID: {}", id);
		boolean deleted = userService.deleteUser(id);
		if (deleted) {
			log.info("Deleted user with ID: {}", id);
			return ResponseEntity.noContent().build();
		} else {
			log.warn("User with ID {} not found for deletion", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser) {
		log.info("Updating user with ID: {}", id);
		User updated = userService.updateUser(id, newUser);
		if (updated == null) {
			log.warn("User with ID {} not found for update", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		log.debug("Updated user: {}", updated);
		return ResponseEntity.ok(updated);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User patch) {
		log.info("Patching user with ID: {}", id);
		User user = userService.patchUser(id, patch);
		if (user == null) {
			log.warn("User with ID {} not found for patch", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		log.debug("Patched user: {}", user);
		return ResponseEntity.ok(user);
	}
}
