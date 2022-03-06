package com.stackroute.foodservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.foodservice.exception.FoodNotFoundException;
import com.stackroute.foodservice.model.Food;
import com.stackroute.foodservice.model.Nutrients;
import com.stackroute.foodservice.model.UserFavFood;
import com.stackroute.foodservice.service.FoodService;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {

	private FoodService foodService;
	
	@Autowired
	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping
	public ResponseEntity<UserFavFood> addFavFood(@RequestBody Food favFood){
		UserFavFood favFoodAdded = foodService.addFavFood(favFood);
		if(favFoodAdded != null) {
			logger.info("In controller - {}", "FavFood added: " +favFood);
			return new ResponseEntity<UserFavFood>(favFoodAdded, HttpStatus.CREATED);
		}
		logger.info("In controller - {}", "Food Name: "+ favFood.getFoodName() + " already exists.");
		return new ResponseEntity<UserFavFood>(HttpStatus.CONFLICT);
	}

	@DeleteMapping("/{userId}/{foodName}")
	public ResponseEntity<Food> deleteFavFoodByName(@PathVariable("userId") String userId, @PathVariable("foodName") String foodName){
		Boolean isFoodDeleted = foodService.deleteFavFoodByName(userId, foodName);
		if(isFoodDeleted == true) {
			logger.info("In controller - {}", "Food deleted for user ID: "+userId+ " and food name: " +foodName);
			return new ResponseEntity<Food>(HttpStatus.OK);
		}
		logger.info("In controller - {}", "Food not found for user ID: "+userId+ " and Food Name: " +foodName);
		return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Food> deleteAllFavFood(@PathVariable("userId") String userId){
		try {
			Boolean isFoodDeleted = foodService.deleteAllFavFood(userId);
			if(isFoodDeleted == true) {
				logger.info("In controller - {}", "All Food deleted for User ID - " +userId);
				return new ResponseEntity<Food>(HttpStatus.OK);
			}
		}
		catch (FoodNotFoundException e) {
			logger.info("In controller - {}", "Food not found for User ID - " +userId);
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "Food not found for User ID - " +userId);
		return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{userId}/{foodName}")
	public ResponseEntity<UserFavFood> updateFavFood(@PathVariable("userId") String userId, @PathVariable("foodName") String foodName, @RequestBody Food food) 
		{
		try {
			UserFavFood foodUpdated = foodService.updateFavFood(food, foodName, userId);
			if(foodUpdated != null) {
				logger.info("In controller - {}", "Food updated for User ID: "+userId+ " and Food Name: " +foodName + " is: " +food);
				return new ResponseEntity<UserFavFood>(foodUpdated, HttpStatus.OK);
			}
		} catch (FoodNotFoundException e) {
			logger.info("In controller - {}", "Food not found for User ID: "+userId+ " and Food Name: " +foodName);
			return new ResponseEntity<UserFavFood>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "Food not found for User ID: "+userId+ " and Food Name: " +foodName);
		return new ResponseEntity<UserFavFood>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/nutrients/{foodName}")		
	public ResponseEntity<Nutrients> getNutrition(@PathVariable("foodName") String foodName){
		Nutrients nutrients;
		try {
			nutrients = foodService.getNutritionByFoodName(foodName);
			if(nutrients != null) {
				logger.info("In controller - {}", "The Nutrients for food Name: " +foodName+ " is: "+nutrients);
				return new ResponseEntity<Nutrients>(nutrients, HttpStatus.OK);
			}
		} catch (FoodNotFoundException e) {
			logger.info("In controller - {}", "Food Name "+foodName+ " not Found.");
			return new ResponseEntity<Nutrients>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "Food Name "+foodName+ " not Found.");
		return new ResponseEntity<Nutrients>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/foodsByCategory/{foodName}")	
	public ResponseEntity<List<Food>> getFoodsByFoodCategory(@PathVariable("foodName") String foodName){
		List<Food> foodList;
		try {
			foodList = foodService.getFoodsByFoodCategory(foodName);
			if(foodList != null) {
				logger.info("In controller - {}", "The Foods for food Name: " +foodName+ " is: "+foodList);
				return new ResponseEntity<List<Food>>(foodList, HttpStatus.OK);
			}
		} catch (FoodNotFoundException e) {
			logger.info("In controller - {}", "Food Name "+foodName+ " not Found.");
			return new ResponseEntity<List<Food>>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "Food Name "+foodName+ " not Found.");
		return new ResponseEntity<List<Food>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/allFavFood/{userId}")
	public ResponseEntity<List<Food>> getAllFavFoodByUserId(@PathVariable("userId") String userId){
		List<Food> allFood = foodService.getAllFavFoodByUserId(userId);
		if(allFood != null) {
			logger.info("In controller - {}", "List of all news: "+allFood);
			return new ResponseEntity<List<Food>>(allFood, HttpStatus.OK);
		}
		else {
			logger.info("In controller - {}", "User ID "+userId+ " not Found.");
			return new ResponseEntity<List<Food>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/allFavFood")
	public ResponseEntity<List<UserFavFood>> getAllFavFood(){
		List<UserFavFood> allFood = foodService.getAllFavFood();
		if(allFood != null) {
			logger.info("In controller - {}", "List of all news: "+allFood);
			return new ResponseEntity<List<UserFavFood>>(allFood, HttpStatus.OK);
		}
		else {
			logger.info("In controller - {}", "No Food Found.");
			return new ResponseEntity<List<UserFavFood>>(HttpStatus.NOT_FOUND);
		}
	}

}
