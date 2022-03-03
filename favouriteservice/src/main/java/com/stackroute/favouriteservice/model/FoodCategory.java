package com.stackroute.favouriteservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FoodCategory {
	
	@Id
	private String categoryName;
	private String description;
	private List<Food> foodList;
	
	public FoodCategory() {
		
	}

	public FoodCategory(String categoryName, List<Food> foods, String description) {
		super();
		this.categoryName = categoryName;
		this.foodList = foods;
		this.description = description;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Food> getFoods() {
		return foodList;
	}

	public void setFoods(List<Food> foods) {
		this.foodList = foods;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "FoodCategory [categoryName=" + categoryName + ", food=" + foodList + ", description=" + description + "]";
	}
	
}
	
	