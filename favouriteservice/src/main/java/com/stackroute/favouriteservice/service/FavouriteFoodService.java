package com.stackroute.favouriteservice.service;

import java.util.List;

import com.stackroute.favouriteservice.exception.FoodNotFoundException;
import com.stackroute.favouriteservice.model.Food;
import com.stackroute.favouriteservice.model.Nutrients;
import com.stackroute.favouriteservice.model.UserFavFood;

public interface FavouriteFoodService {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	UserFavFood addFavFood(Food favFood);
	
	boolean deleteAllFavFood(String userId) throws FoodNotFoundException;
	
	boolean deleteFavFoodByName(String userId, String foodName);

	UserFavFood updateFavFood(Food food, String foodName, String userId) throws FoodNotFoundException;
	
	Nutrients getNutritionByFoodName(String foodName) throws FoodNotFoundException;

	List<Food> getFoodsByFoodCategory(String foodName) throws FoodNotFoundException;
	
	List<Food> getAllFavFoodByUserId(String userId);
	
	List<UserFavFood> getAllFavFood();

}
