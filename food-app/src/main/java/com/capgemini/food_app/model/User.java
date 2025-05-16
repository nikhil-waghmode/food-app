package com.capgemini.food_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@NotBlank(message = "Name is mandatory.")
	String name;
	
	@Email(message = "Invalid email format.")
	String email;
	
	@NotBlank
	@Size(min=5,message = "Length should minimum 5")
	String password;
	
	@Pattern(regexp = "^\\d{10}$",message = "Invalid phone number.")
	String phone;
	
	@NotBlank(message = "User type is mandatory")
	@Column(name="user_type")
	String userType;
	
	String location;
	
	@Column(name="user_img")
	String userImg;
	
	public User() {
	}

	public User(Long id, String name, String email, String password, String phone, String userType, String location,
			String userImg) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userType = userType;
		this.location = location;
		this.userImg = userImg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", userType=" + userType + ", location=" + location + ", userImg=" + userImg + "]";
	}
}

