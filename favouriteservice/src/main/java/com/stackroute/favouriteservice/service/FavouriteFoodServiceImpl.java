package com.stackroute.favouriteservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.favouriteservice.exception.FoodNotFoundException;
import com.stackroute.favouriteservice.exception.UserNotFoundException;
import com.stackroute.favouriteservice.model.Food;
import com.stackroute.favouriteservice.model.Nutrients;
import com.stackroute.favouriteservice.model.UserFavFood;
import com.stackroute.favouriteservice.repository.FoodServiceRepository;

@Service
public class FavouriteFoodServiceImpl implements FavouriteFoodService {
	
	private FoodServiceRepository foodServiceRepo;
	
	public FavouriteFoodServiceImpl() {
	}
	
	@Autowired
	public FavouriteFoodServiceImpl(FoodServiceRepository foodServiceRepository) {
		this.foodServiceRepo = foodServiceRepository;
	}
	
	@Override
	public UserFavFood addFavFood(Food food) {
		Boolean isExistsUserFood = foodServiceRepo.existsById(food.getCreatedBy());
		if(isExistsUserFood == false) {
			
			Food newFood = new Food();
			List<Food> foodList = new ArrayList<Food>();
			newFood.setDescription(food.getDescription());
			newFood.setFoodCategory(food.getFoodCategory());
			newFood.setFoodName(food.getFoodName());
			newFood.setNutrients(getNutrition(food));
			newFood.setCreatedBy(food.getCreatedBy());
			foodList.add(newFood);
			
			UserFavFood userFood = new UserFavFood();
			userFood.setUserId(food.getCreatedBy());
			userFood.setFavFoodList(foodList);
			UserFavFood userFoodAdded = foodServiceRepo.insert(userFood);
				if(userFoodAdded != null)
					return userFoodAdded;
			}
		return null;
	}

	@Override
	public boolean deleteAllFavFood(String userId) throws FoodNotFoundException {
		try {
			UserFavFood userFood = foodServiceRepo.findById(userId).get();
			List<Food> foodList = userFood.getFavFoodList();
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
		UserFavFood userFood = foodServiceRepo.findById(userId).get();
		List<Food> foodList  = userFood.getFavFoodList();
		for(Food foodByName : foodList) {
			if(foodByName.getFoodName().equalsIgnoreCase(foodName)) {
				foodServiceRepo.deleteById(foodByName.getCreatedBy());
			return true;
			}
		}
		return false;
	}

	@Override
	public UserFavFood updateFavFood(Food food, String foodName, String userId) throws FoodNotFoundException {
		int index = 0;
		try {
			UserFavFood userFood = foodServiceRepo.findById(userId).get();
			List<Food> foodList  = userFood.getFavFoodList();
			if(foodList != null) {
			for(Food foodByName : foodList) {
				if(foodByName.getFoodName().equalsIgnoreCase(foodName)) {
					if(!foodByName.getFoodName().equalsIgnoreCase(food.getFoodName())) {
						throw new FoodNotFoundException("Food name cannot be modified.");
					}
					else if(!foodByName.getCreatedBy().equalsIgnoreCase(food.getCreatedBy())) {
						throw new FoodNotFoundException("Food created by user cannot be modified.");
					}
					foodByName.setDescription(food.getDescription());
					foodByName.setFoodCategory(food.getFoodCategory());
					foodByName.setNutrients(getNutrition(food));
					foodList.set(index, foodByName);
					index++;
				}
			}
			userFood.setFavFoodList(foodList);
			foodServiceRepo.save(userFood);
			return userFood;
			}}
			catch(NoSuchElementException ex) {
				throw new FoodNotFoundException("Can not Update the Food. The food with user ID: "+userId+ " and food Name: "+foodName+" does not exists in the database.");
			}
			return null;
	}

	@Override
	public Nutrients getNutritionByFoodName(String foodName) throws FoodNotFoundException {
		try {
			List<UserFavFood> userFoodList = foodServiceRepo.findAll();
			List<Food> foodList = null;
			Nutrients nutrients = null;
			
			if(userFoodList != null) {
				for(UserFavFood userFood:userFoodList) {
					foodList = userFood.getFavFoodList();
					if(foodList != null) {
						for(Food food:foodList) {
							if(food.getFoodName().equalsIgnoreCase(foodName)) {
								nutrients = getNutrition(food);
								return nutrients;
							}}}
				}}}
		catch(NoSuchElementException e) {
				throw new FoodNotFoundException("Can not Retrieve the Food. The Food with Name: "+foodName+ " and food name: "+foodName +" does not exists in the database.");
			}
		return null;
	}
	
	@Override
	public List<Food> getFoodsByFoodCategory(String foodCategoryName) throws FoodNotFoundException {
	List<Food> foodListByCategory = new ArrayList<Food>();
	List<Food> foodList = null;

	try {
		List<UserFavFood> userFoodList = foodServiceRepo.findAll();
		if(userFoodList != null) {
			for(UserFavFood userFood:userFoodList) {
				foodList = userFood.getFavFoodList();
				if(foodList != null) {
					for(Food food:foodList) {
						if(food.getFoodCategory().equalsIgnoreCase(foodCategoryName)) {
								Nutrients nutrients = getNutrition(food);
								food.setNutrients(nutrients);
								foodListByCategory.add(food);
						}}
			}}}
		return foodListByCategory;
		}
	catch(NoSuchElementException e) {
			throw new FoodNotFoundException("Can not Retrieve the Food. The Food with Name: "+foodCategoryName+ " does not exists in the database.");
		}
	}

	@Override
	public List<Food> getAllFavFoodByUserId(String userId) {
		UserFavFood favFood = foodServiceRepo.findById(userId).get();
		if(favFood != null) {
			return favFood.getFavFoodList();
		}
		return null;
	}
	
	@Override
	public List<UserFavFood> getAllFavFood(){
		return foodServiceRepo.findAll();
	}
	
	private Nutrients getNutrition(Food food) {
		Nutrients nutrition = new Nutrients();
		nutrition.setTotalCalories(food.getNutrients().getTotalCalories());
		nutrition.setTotalCarbohydrates(food.getNutrients().getTotalCarbohydrates());
		nutrition.setTotalFat(food.getNutrients().getTotalFat());
		nutrition.setTotalProtein(food.getNutrients().getTotalProtein());
		nutrition.setPerServing(food.getNutrients().getPerServing());
		return nutrition;
	}

}
