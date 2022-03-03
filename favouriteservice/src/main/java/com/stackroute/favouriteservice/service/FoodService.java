package com.stackroute.favouriteservice.service;

import java.util.List;

import com.stackroute.favouriteservice.exception.FoodNotFoundException;
import com.stackroute.favouriteservice.model.FavouriteFood;
import com.stackroute.favouriteservice.model.Food;

public interface FoodService {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	boolean addFavFoodByfoodName(FavouriteFood favFood);
	
	boolean deleteAllFavFood(String userId) throws FoodNotFoundException;
	
	boolean deleteFavFoodByName(String userId, String foodName);

	Food updateFavFood(Food food, String foodName, String userId) throws FoodNotFoundException;

	Food getFavFoodByFoodName(String userId, String foodName) throws FoodNotFoundException;
	
	List<Food> getAllFavFoodByUserId(String userId);

}
