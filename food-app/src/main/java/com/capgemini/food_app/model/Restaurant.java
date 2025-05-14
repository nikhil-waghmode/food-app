package com.capgemini.food_app.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String location;
	private String contact;
	private Long ownerId;
	private String restaurantImg;
	
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


	public Restaurant(Long id, String name, String location, String contact, Long ownerId, String restaurantImg,
			List<FoodItem> foodItem) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.contact = contact;
		this.ownerId = ownerId;
		this.restaurantImg = restaurantImg;
		this.foodItem = foodItem;
	}
	
	

	public Restaurant(Long id, String name, String location, String contact, Long ownerId, String restaurantImg,
			List<FoodItem> foodItem, List<Reviews> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.contact = contact;
		this.ownerId = ownerId;
		this.restaurantImg = restaurantImg;
		this.foodItem = foodItem;
		this.reviews = reviews;
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

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getRestaurantImg() {
		return restaurantImg;
	}


	public void setRestaurantImg(String restaurantImg) {
		this.restaurantImg = restaurantImg;
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
		return "Restaurant [id=" + id + ", name=" + name + ", location=" + location + ", contact=" + contact + ", ownerId="
				+ ownerId + ", restaurantImg=" + restaurantImg + "]";
	}
	
	

}