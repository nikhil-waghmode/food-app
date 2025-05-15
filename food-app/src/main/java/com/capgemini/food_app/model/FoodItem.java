package com.capgemini.food_app.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="food_items")
public class FoodItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Category is mandatory")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    private String category;

    @NotNull(message = "Price is mandatory")
    @Min(value = 1, message = "Price must be at least 1")
    private Integer price;

    @NotBlank(message = "Item image URL is mandatory")
    @Size(max = 255, message = "Item image URL cannot exceed 255 characters")
    private String itemImg;

    @NotBlank(message = "Cuisine is mandatory")
    @Size(max = 50, message = "Cuisine cannot exceed 50 characters")
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

	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
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
