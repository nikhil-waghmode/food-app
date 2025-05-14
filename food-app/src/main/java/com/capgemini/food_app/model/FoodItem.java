package com.capgemini.food_app.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="food_items")
public class FoodItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String category;
	private Integer price;
	private String itemImg;
	private String cuisine;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	public FoodItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FoodItem(Long id, String name, String category, Integer price, String itemImg) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.itemImg = itemImg;
	}

	public FoodItem(Long id, String name, String category, Integer price, String itemImg, String cuisine) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.itemImg = itemImg;
		this.cuisine = cuisine;
	}

	public FoodItem(Long id, String name, String category, Integer price, String itemImg, Restaurant restaurant) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.itemImg = itemImg;
		this.restaurant = restaurant;
	}
	
	

	public FoodItem(Long id, String name, String category, Integer price, String itemImg, String cuisine,
			Restaurant restaurant) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.itemImg = itemImg;
		this.cuisine = cuisine;
		this.restaurant = restaurant;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getitemImg() {
		return itemImg;
	}

	public void setitemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "FoodItem [id=" + id + ", name=" + name + ", category=" + category + ", price=" + price + ", itemImg="
				+ itemImg + "]";
	}
	
	

}
