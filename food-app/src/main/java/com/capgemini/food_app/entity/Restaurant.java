package com.capgemini.food_app.entity;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * "name": "Tasty Bites", "location": "Downtown", "phone": "9876543210",
 * "ownerID": "u1a9b", "id": "r2k9d"
 */
@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String location;
	private String phone;
	private Long ownerId;
	private String resImage;
	
	@OneToMany(mappedBy="restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<FoodItem> foodItem = new ArrayList<FoodItem>();
	
	@OneToMany(mappedBy="restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Reviews> reviews = new ArrayList<Reviews>();

	public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Restaurant(Long id, String name, String location, String phone, Long ownerId, String resImage,
			List<FoodItem> foodItem, List<Reviews> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.phone = phone;
		this.ownerId = ownerId;
		this.resImage = resImage;
		this.foodItem = foodItem;
		this.reviews = reviews;
	}

	public Restaurant(Long id, String name, String location, String phone, Long ownerId, String resImage,
			List<FoodItem> foodItem) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.phone = phone;
		this.ownerId = ownerId;
		this.resImage = resImage;
		this.foodItem = foodItem;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getResImage() {
		return resImage;
	}

	public void setResImage(String resImage) {
		this.resImage = resImage;
	}

	public List<FoodItem> getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(List<FoodItem> foodItem) {
		this.foodItem = foodItem;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}

	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", location=" + location + ", phone=" + phone + ", ownerId="
				+ ownerId + ", resImage=" + resImage + "]";
	}
	
	

}