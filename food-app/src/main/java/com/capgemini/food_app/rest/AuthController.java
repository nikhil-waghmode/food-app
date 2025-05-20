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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.dto.LoginDTO;
import com.capgemini.food_app.exception.EmailAlreadyExistsException;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.model.User;
import com.capgemini.food_app.security.JwtUtils;
import com.capgemini.food_app.service.RestaurantService;
import com.capgemini.food_app.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	AuthenticationManager authenticationManager;
	UserService userService;
	PasswordEncoder passwordEncoder;
	JwtUtils jwtService;
	RestaurantService restaurantService;
	
	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserService userService, RestaurantService restaurantService,
			PasswordEncoder passwordEncoder, JwtUtils jwtService) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.restaurantService = restaurantService;
	}

	@PostMapping("/signin")
	public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginDTO loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		if (authentication.isAuthenticated()) {
			User user = userService.findByEmail(loginDto.getEmail());
			Map<String, Object> claims = new HashMap<>();
			claims.put("email", user.getEmail());
			claims.put("userid", user.getId());
			claims.put("usertype", user.getUserType());
			
			if (user.getUserType().equals("OWNER")) {
			    Restaurant restaurant = restaurantService.getRestaurantByOwner(user.getId());
			    if (restaurant != null) {
			        claims.put("currentRestaurantId", restaurant.getId());
			    } else {
			        System.out.println("No restaurant found for OWNER with id: " + user.getId());
			        // Optionally set: claims.put("currentRestaurantId", null);
			    }
			}

			String token = jwtService.generateToken(loginDto.getEmail(), claims);

			Map<String, String> response = new HashMap<>();
			response.put("token", token);

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		Map<String, String> error = new HashMap<>();
		error.put("error", "You are not Authorized !!");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(
	        @RequestParam String name,
	        @RequestParam String email,
	        @RequestParam String password,
	        @RequestParam String phone,
	        @RequestParam String location,
	        @RequestParam String userType,
	        @RequestParam(required = false) String restaurantName,
	        @RequestParam(required = false) String restaurantLocation,
	        @RequestParam(required = false) String restaurantPhone,
	        @RequestParam(value = "userImg", required = false) MultipartFile profileImage,
	        @RequestParam(value = "resImage", required = false) MultipartFile restaurantImage // optional
	) throws IOException {

	    if (userService.existsByEmail(email)) {
	        throw new EmailAlreadyExistsException("Email Exists!");
	    }

	    String encodedPassword = passwordEncoder.encode(password);

	    User createdUser = userService.createUser(name, email, encodedPassword, phone, location, userType, profileImage);

	    if ("OWNER".equalsIgnoreCase(userType)) {
	        if (restaurantName == null || restaurantLocation == null || restaurantPhone == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(null); // Or return a proper error response with a message
	        }

	        restaurantService.createRestaurant(
	                restaurantName,
	                restaurantLocation,
	                restaurantPhone,
	                createdUser.getId(),
	                restaurantImage
	        );
	    }

	    return ResponseEntity.status(HttpStatus.OK).body(createdUser);
	}

	@GetMapping("/exists")
	public ResponseEntity<Boolean> checkUserExists(@RequestParam String email) {
	    boolean exists = userService.existsByEmail(email);
	    return ResponseEntity.ok(exists);
	}

}
