package com.stackroute.favouriteservice.controller;

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

import com.stackroute.favouriteservice.exception.FoodNotFoundException;
import com.stackroute.favouriteservice.model.FavouriteFood;
import com.stackroute.favouriteservice.model.Food;
import com.stackroute.favouriteservice.service.FoodService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping("/api/v1/food")
public class FoodController {

	private FoodService foodService;
	
	@Autowired
	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	public ResponseEntity<FavouriteFood> getfood(){
		return new ResponseEntity<FavouriteFood>(HttpStatus.ACCEPTED);
	}

	@PostMapping
	public ResponseEntity<FavouriteFood> addFavFood(@RequestBody FavouriteFood favFood){
		Boolean isFavFoodExists = foodService.addFavFoodByfoodName(favFood);
		if(isFavFoodExists == true) {
			logger.info("In controller - {}", "FavFood added: " +favFood);
			return new ResponseEntity<FavouriteFood>(favFood, HttpStatus.CREATED);
		}
		logger.info("In controller - {}", "User ID "+ favFood.getUserId() + " already exists.");
		return new ResponseEntity<FavouriteFood>(HttpStatus.CONFLICT);
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
		List<Food> allFood;
		try {
			allFood = foodService.getAllFavFoodByUserId(userId);
			if(allFood != null) {
			foodService.deleteAllFavFood(userId);
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
	public ResponseEntity<Food> updateFavFood(@PathVariable("userId") String userId, @PathVariable("foodName") String foodName, @RequestBody Food food) 
		{
		try {
				Food foodUpdated = foodService.updateFavFood(food, foodName, userId);
				if(foodUpdated != null) {
				logger.info("In controller - {}", "Food updated for User ID: "+userId+ " and Food Name: " +foodName + " is: " +food);
				return new ResponseEntity<Food>(foodUpdated, HttpStatus.OK);
			}
		} catch (FoodNotFoundException e) {
			logger.info("In controller - {}", "Food not found for User ID: "+userId+ " and Food Name: " +foodName);
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "Food not found for User ID: "+userId+ " and Food Name: " +foodName);
		return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{userId}/{foodName}")
	public ResponseEntity<Food> getFavFoodByFoodName(@PathVariable("userId") String userId, @PathVariable("foodName") String foodName){
		Food foodByName;
		try {
			foodByName = foodService.getFavFoodByFoodName(userId, foodName);
			if(foodByName != null) {
				logger.info("In controller - {}", "The Food for User ID: "+userId+ " and food Name: " +foodName+ " is: "+foodByName);
				return new ResponseEntity<Food>(foodByName, HttpStatus.OK);
			}
		} catch (FoodNotFoundException e) {
			logger.info("In controller - {}", "Food Name "+foodName+ " not Found.");
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "Food Name "+foodName+ " not Found.");
		return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<Food>> getAllNewsByUserId(@PathVariable("userId") String userId){
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

}
