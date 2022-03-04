package com.stackroute.foodservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserFavFood {
	
	@Id
	private String userId;
	private List<Food> favFoodList;
	
	public UserFavFood() {
		
	}
	
	public UserFavFood(String userId, List<Food> favFoodList) {
		super();
		this.userId = userId;
		this.favFoodList = favFoodList;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public List<Food> getFavFoodList() {
		return favFoodList;
	}
	
	public void setFavFoodList(List<Food> favFoodList) {
		this.favFoodList = favFoodList;
	}
	
	@Override
	public String toString() {
		return "UserFavFood [userId=" + userId + ", favFoodList=" + favFoodList + "]";
	}
	
}
