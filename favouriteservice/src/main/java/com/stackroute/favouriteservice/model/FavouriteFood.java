package com.stackroute.favouriteservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class FavouriteFood {
	
	@Id
	private String userId;
	private List<Food> listFavFoods;
	@JsonSerialize(using = ToStringSerializer.class) 
	private LocalDateTime createdAt;
	
	public FavouriteFood() {
		
	}

	public FavouriteFood(String userId, List<Food> favFoods, LocalDateTime createdAt) {
		super();
		this.userId = userId;
		this.listFavFoods = favFoods;
		this.createdAt = createdAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Food> getFavFoods() {
		return listFavFoods;
	}

	public void setFavFoods(List<Food> favFoods) {
		this.listFavFoods = favFoods;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "FavouriteFood [userId=" + userId + ", favFoods=" + listFavFoods + ", createdAt=" + createdAt + "]";
	}
	
}
	
