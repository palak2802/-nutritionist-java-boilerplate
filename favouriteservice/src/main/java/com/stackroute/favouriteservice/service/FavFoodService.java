package com.stackroute.favouriteservice.service;

import java.util.List;

import com.stackroute.favouriteservice.exception.FoodNotFoundException;
import com.stackroute.favouriteservice.model.FavouriteFood;

public interface FavFoodService {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	boolean addFavouriteFood(FavouriteFood favFood);

	boolean deleteFavouriteFood(int favFoodId);

	FavouriteFood updateFavouriteFood(FavouriteFood favFood, int favFoodId) throws FoodNotFoundException;

	FavouriteFood getFavouriteFoodById(String userId,int favFoodId) throws FoodNotFoundException;

	List<FavouriteFood> getAllFavouriteFoodByUserId(String userId);

}
