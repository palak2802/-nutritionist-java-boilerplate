package com.stackroute.favouriteservice.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.favouriteservice.exception.FoodNotFoundException;
import com.stackroute.favouriteservice.model.FavouriteFood;
import com.stackroute.favouriteservice.model.Food;
import com.stackroute.favouriteservice.repository.FoodServiceRepository;

@Service
public class FoodServiceImpl implements FoodService {
	
	private FoodServiceRepository foodServiceRepo;
	
	public FoodServiceImpl() {
	}
	
	@Autowired
	public FoodServiceImpl(FoodServiceRepository foodServiceRepository) {
		this.foodServiceRepo = foodServiceRepository;
	}

	@Override
	public boolean addFavFoodByfoodName(FavouriteFood favFood) {
		Boolean isExistsUserFood = foodServiceRepo.existsById(favFood.getUserId());
		if(isExistsUserFood == false) {
			FavouriteFood userFavFood = new FavouriteFood();
			userFavFood.setUserId(favFood.getUserId());
			userFavFood.setFavFoods(favFood.getFavFoods());
			FavouriteFood userFoodAdded = foodServiceRepo.insert(userFavFood);
				if(userFoodAdded != null)
					return true;
				else
					return false;
			}
		return false;
	}

	@Override
	public boolean deleteAllFavFood(String userId) throws FoodNotFoundException {
		try {
			FavouriteFood userFood = foodServiceRepo.findById(userId).get();
			List<Food> foodList = userFood.getFavFoods();
			if(foodList != null) {
				foodServiceRepo.delete(userFood);
				return true;
			}}
			catch(NoSuchElementException ex) {
				throw new FoodNotFoundException("Can not Delete the Food. The Food with user ID: "+userId+ " does not exists in the database.");
			}
			return false;
	}

	@Override
	public boolean deleteFavFoodByName(String userId, String foodName) {
		FavouriteFood userFood = foodServiceRepo.findById(userId).get();
		List<Food> foodList  = userFood.getFavFoods();
		for(Food foodByName : foodList) {
			if(foodByName.getFoodName().equalsIgnoreCase(foodName)) {
				foodServiceRepo.deleteById(foodByName.getFoodName());
			return true;
			}
		}
		return false;
	}

	@Override
	public Food updateFavFood(Food food, String foodName, String userId) throws FoodNotFoundException {
		try {
			FavouriteFood userFood = foodServiceRepo.findById(userId).get();
			List<Food> foodList  = userFood.getFavFoods();
			for(Food foodByName : foodList) {
				if(foodByName.getFoodName().equalsIgnoreCase(foodName)) {
					foodByName.setNutrients(food.getNutrients());
					foodByName.setDescription(food.getDescription());
					foodList.add(foodByName);
					userFood.setFavFoods(foodList);
					foodServiceRepo.save(userFood);
					return foodByName;
				}
			}}
			catch(NoSuchElementException ex) {
				throw new FoodNotFoundException("Can not Update the Food. The food with user ID: "+userId+ " and food Name: "+foodName+" does not exists in the database.");
			}
			return null;
	}

	@Override
	public Food getFavFoodByFoodName(String userId, String foodName) throws FoodNotFoundException {
		try {
			FavouriteFood userFood = foodServiceRepo.findById(userId).get();
			List<Food> foodList = userFood.getFavFoods();
			for(Food foodByName : foodList) {
				if(foodByName.getFoodName().equalsIgnoreCase(foodName)){
				return foodByName;
					}
				}
			}
		catch(NoSuchElementException e) {
				throw new FoodNotFoundException("Can not Retrieve the Food. The Food with user ID: "+userId+ " and food name: "+foodName +" does not exists in the database.");
			}
		return null;
	}

	@Override
	public List<Food> getAllFavFoodByUserId(String userId) {
		return foodServiceRepo.findById(userId).get().getFavFoods();
	}

}
