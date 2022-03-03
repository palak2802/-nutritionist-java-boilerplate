package com.stackroute.favouriteservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Food {
	
	@Id
	private String foodName;
	private String description;
	private List<Nutrients> nutrientsList;
	
	public Food(String foodName, String description, List<Nutrients> nutrients) {
		super();
		this.foodName = foodName;
		this.description = description;
		this.nutrientsList = nutrients;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Nutrients> getNutrients() {
		return nutrientsList;
	}

	public void setNutrients(List<Nutrients> nutrients) {
		this.nutrientsList = nutrients;
	}

	@Override
	public String toString() {
		return "Food [foodName=" + foodName + ", description=" + description + ", nutrients=" + nutrientsList + "]";
	}
	
}
