package com.stackroute.favouriteservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class FavouriteFood {
	
	/*
	 * Please note that this class is annotated with @Document annotation
	 * @Document identifies a domain object to be persisted to MongoDB.
	 *  
	 */
	@Id
	private int foodId;
	private String favFoodName;
	private String favFoodDesc;
	private String favFoodCreatedBy;
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime favFoodCreationDate;
	
	public FavouriteFood() {
		this.favFoodCreationDate = LocalDateTime.now();
	}

	public FavouriteFood(int foodId, String favFoodName, String favFoodDesc, String favFoodCreatedBy,
			LocalDateTime favFoodCreationDate) {
		super();
		this.foodId = foodId;
		this.favFoodName = favFoodName;
		this.favFoodDesc = favFoodDesc;
		this.favFoodCreatedBy = favFoodCreatedBy;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getFavFoodName() {
		return favFoodName;
	}

	public void setFavFoodName(String favFoodName) {
		this.favFoodName = favFoodName;
	}

	public String getFavFoodDesc() {
		return favFoodDesc;
	}

	public void setFavFoodDesc(String favFoodDesc) {
		this.favFoodDesc = favFoodDesc;
	}

	public String getFavFoodCreatedBy() {
		return favFoodCreatedBy;
	}

	public void setFavFoodCreatedBy(String favFoodCreatedBy) {
		this.favFoodCreatedBy = favFoodCreatedBy;
	}

	public LocalDateTime getFavFoodCreationDate() {
		return favFoodCreationDate;
	}

	public void setFavFoodCreationDate() {
		this.favFoodCreationDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "FavouriteFood [foodId=" + foodId + ", favFoodName=" + favFoodName + ", favFoodDesc=" + favFoodDesc
				+ ", favFoodCreatedBy=" + favFoodCreatedBy + ", favFoodCreationDate=" + favFoodCreationDate + "]";
	}
	
}
