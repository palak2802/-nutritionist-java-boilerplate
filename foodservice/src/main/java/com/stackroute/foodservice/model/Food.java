package com.stackroute.foodservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class Food {
	
	@Id
	private String foodName;
	private String foodCategory;
	private String description;
	private String createdBy;
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime creationDate;
	private Nutrients nutrients;
	
	public Food(){
		this.creationDate = LocalDateTime.now();
	}

	public Food(String foodName, String foodCategory, String description, String createdBy, LocalDateTime creationDate,
			Nutrients nutrients) {
		super();
		this.foodName = foodName;
		this.foodCategory = foodCategory;
		this.description = description;
		this.createdBy = createdBy;
		this.nutrients = nutrients;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate() {
		this.creationDate = LocalDateTime.now();
	}

	public Nutrients getNutrients() {
		return nutrients;
	}

	public void setNutrients(Nutrients nutrients) {
		this.nutrients = nutrients;
	}

	@Override
	public String toString() {
		return "Food [foodName=" + foodName + ", foodCategory=" + foodCategory + ", description=" + description
				+ ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", nutrients=" + nutrients +"]";
	}
	
}
