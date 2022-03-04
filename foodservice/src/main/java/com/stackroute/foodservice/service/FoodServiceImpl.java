package com.stackroute.foodservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.foodservice.exception.FoodNotFoundException;
import com.stackroute.foodservice.model.Food;
import com.stackroute.foodservice.model.Nutrients;
import com.stackroute.foodservice.model.UserFavFood;
import com.stackroute.foodservice.repository.FoodServiceRepository;

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
	public boolean addFood(Food food) {
		Food ifFoodExists = foodServiceRepo.findFoodByFoodName(food.getFoodName());
		if(ifFoodExists == null) {
			Food newFood = new Food();
			newFood.setDescription(food.getDescription());
			newFood.setFoodCategory(food.getFoodCategory());
			newFood.setFoodName(food.getFoodName());
			newFood.setNutrients(food.getNutrients());
			newFood.setCreatedBy(food.getCreatedBy());
			Food foodSaved = foodServiceRepo.saveFood(newFood);
			if(foodSaved != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addFavFood(Food food) {
		Boolean isExistsUserFood = foodServiceRepo.existsById(food.getCreatedBy());
		if(isExistsUserFood == false) {
			List<Food> foodList = new ArrayList<Food>();
			UserFavFood userFood = new UserFavFood();
			foodList.add(food);
			userFood.setUserId(food.getCreatedBy());
			userFood.setFavFoodList(foodList);
			UserFavFood userFoodAdded = foodServiceRepo.insert(userFood);
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
				foodServiceRepo.deleteById(foodByName.getFoodName());
			return true;
			}
		}
		return false;
	}

	@Override
	public Food updateFavFood(Food food, String foodName, String userId) throws FoodNotFoundException {
		try {
			UserFavFood userFood = foodServiceRepo.findById(userId).get();
			List<Food> foodList  = userFood.getFavFoodList();
			if(foodList != null) {
			for(Food foodByName : foodList) {
				if(foodByName.getFoodName().equalsIgnoreCase(foodName)) {
					foodByName.setCreatedBy(food.getCreatedBy());
					foodByName.setDescription(food.getDescription());
					foodByName.setFoodCategory(food.getFoodCategory());
					foodByName.setFoodName(food.getFoodName());
					foodByName.setNutrients(food.getNutrients());
					foodList.add(foodByName);
					userFood.setFavFoodList(foodList);
					foodServiceRepo.save(userFood);
					return foodByName;
				}
			}}}
			catch(NoSuchElementException ex) {
				throw new FoodNotFoundException("Can not Update the Food. The food with user ID: "+userId+ " and food Name: "+foodName+" does not exists in the database.");
			}
			return null;
	}

	@Override
	public Nutrients getNutritionByFoodName(String foodName) throws FoodNotFoundException {
		try {
			Food food = foodServiceRepo.findFoodByFoodName(foodName);
			if(food != null) {
				return food.getNutrients();
				}
			}
		catch(NoSuchElementException e) {
				throw new FoodNotFoundException("Can not Retrieve the Food. The Food with Name: "+foodName+ " and food name: "+foodName +" does not exists in the database.");
			}
		return null;
	}
	
	@Override
	public List<Food> getFoodsByFoodCategory(String foodName) throws FoodNotFoundException {
		String foodCategory = null;
		List<Food> foodByCategoryList;
		try {
			Food food = foodServiceRepo.findFoodByFoodName(foodName);
			if(food != null) {
				foodCategory = food.getFoodCategory();
			}
			
			List<Food> foodList = foodServiceRepo.findAllFoods();
			if(foodList != null) {
			foodByCategoryList = new ArrayList<Food>();
			for(Food foodByCategory : foodList) {
				if(foodCategory.equals(foodByCategory.getFoodCategory())) {
					foodByCategoryList.add(foodByCategory);
				}
			}
			return foodByCategoryList;
			}}
		catch(NoSuchElementException e) {
				throw new FoodNotFoundException("Can not Retrieve the Food. The Food with Name: "+foodName+ " does not exists in the database.");
			}
		return null;
	}

	@Override
	public List<Food> getAllFavFoodByUserId(String userId) {
		return foodServiceRepo.findById(userId).get().getFavFoodList();
	}

}
