package com.capgemini.food_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.food_app.model.User;
import com.capgemini.food_app.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users=userService.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User saved=userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		User user=userService.getUserById(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		boolean deleted=userService.deleteUser(id);
		if (deleted) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser){
		User updated=userService.updateUser(id, newUser);
		if(updated!=null)
			return ResponseEntity.status(HttpStatus.OK).body(updated);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User patch){
		User user=userService.patchUser(id, patch);
		if(user!=null)
			return ResponseEntity.status(HttpStatus.OK).body(user);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
