package com.stackroute.foodservice.service;

import java.util.List;

import com.stackroute.foodservice.exception.FoodNotFoundException;
import com.stackroute.foodservice.model.Food;
import com.stackroute.foodservice.model.Nutrients;

public interface FoodService {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	boolean addFood(Food food);
	
	boolean addFavFood(Food favFood);
	
	boolean deleteAllFavFood(String userId) throws FoodNotFoundException;
	
	boolean deleteFavFoodByName(String userId, String foodName);

	Food updateFavFood(Food food, String foodName, String userId) throws FoodNotFoundException;
	
	Nutrients getNutritionByFoodName(String foodName) throws FoodNotFoundException;

	List<Food> getFoodsByFoodCategory(String foodName) throws FoodNotFoundException;
	
	List<Food> getAllFavFoodByUserId(String userId);

}
