/**
 * 
 */
package com.capgemini.food_app.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.food_app.dto.LoginDTO;
import com.capgemini.food_app.exception.UserAlreadyExistsException;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.model.User;
import com.capgemini.food_app.security.JwtUtils;
import com.capgemini.food_app.service.RestaurantService;
import com.capgemini.food_app.service.UserService;


/**
 * 
 */

@RestController
@RequestMapping("/auth")
public class AuthController {
	AuthenticationManager authenticationManager;
	UserService userService;
	RestaurantService restaurantService;
	PasswordEncoder passwordEncoder;
	JwtUtils jwtService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserService userService,
			PasswordEncoder passwordEncoder, JwtUtils jwtService, RestaurantService restaurantService) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.restaurantService = restaurantService;
		this.jwtService = jwtService;
	}

	@PostMapping("/signin")
	public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginDTO loginDTO) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

		if (authentication.isAuthenticated()) {
			User user = userService.findByEmail(loginDTO.getEmail());
			Map<String, Object> claims = new HashMap<>();
			claims.put("email", user.getEmail());
			claims.put("userid", user.getId());
			claims.put("usertype", user.getUserType());
			if (user.getUserType().equals("OWNER")) {
			    Restaurant restaurant = restaurantService.getRestaurantByOwner(user.getId());
			    claims.put("currentRestaurantId", restaurant.getId());
			    System.out.println(restaurant.getId());
			}

			String token = jwtService.generateToken(loginDTO.getEmail(), claims);

			Map<String, String> response = new HashMap<>();
			response.put("token", token);

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		Map<String, String> error = new HashMap<>();
		error.put("error", "You are not Authorized !!");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) throws IOException {
		if (userService.existsByEmail(user.getEmail()))
			throw new UserAlreadyExistsException("Email Exists !");
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return ResponseEntity.status(HttpStatus.OK).body(userService.createUser1(user));
	}
}
