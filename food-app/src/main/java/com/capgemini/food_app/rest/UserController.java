package com.capgemini.food_app.rest;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.model.User;
import com.capgemini.food_app.service.UserService;

@RestController
@RequestMapping("api/users")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('OWNER')")

public class UserController {

	UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> createUser(
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String phone,
			@RequestParam String location,
			@RequestParam String userType,
			@RequestParam(value = "userImg", required = false) MultipartFile profileImage) throws IOException {
		User saved = userService.createUser(name, email, password, phone, location, userType, profileImage);
		return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(saved);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		boolean deleted = userService.deleteUser(id);
		if (deleted) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> updateUser(
			@PathVariable Long id,
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String phone,
			@RequestParam String location,
			@RequestParam String userType,
			@RequestParam(value = "userImg", required = false) MultipartFile profileImage) throws IOException {
		User updated = userService.updateUser(id, name, email, password, phone, location, userType, profileImage);
		if (updated != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updated);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PatchMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> patchUser(
			@PathVariable Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "userType", required = false) String userType,
			@RequestParam(value = "userImg", required = false) MultipartFile profileImage) throws IOException {
		User patched = userService.patchUser(id, name, email, password, phone, location, userType, profileImage);
		if (patched != null) {
			return ResponseEntity.status(HttpStatus.OK).body(patched);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}