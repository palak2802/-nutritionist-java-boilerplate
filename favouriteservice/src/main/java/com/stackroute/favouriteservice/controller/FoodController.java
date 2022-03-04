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
import com.stackroute.favouriteservice.service.FavFoodService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping("/api/v1/favfood")
public class FoodController {

	private FavFoodService favFoodService;
	
	@Autowired
	public FoodController(FavFoodService favFoodService) {
		this.favFoodService = favFoodService;
	}
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping
	public ResponseEntity<FavouriteFood> addFavFood(@RequestBody FavouriteFood favFood){
		Boolean isFavFoodExists = favFoodService.addFavouriteFood(favFood);
		if(isFavFoodExists == true) {
			logger.info("In controller - {}", "FavFood added: " +favFood);
			return new ResponseEntity<FavouriteFood>(favFood, HttpStatus.CREATED);
		}
		logger.info("In controller - {}", "User ID "+ favFood.getFavFoodCreatedBy() + " not exists.");
		return new ResponseEntity<FavouriteFood>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{favFoodId}")
	public ResponseEntity<FavouriteFood> deleteFavouriteFood(@PathVariable("favFoodId") int favFoodId){
		Boolean isFoodDeleted = favFoodService.deleteFavouriteFood(favFoodId);
		if(isFoodDeleted == true) {
			logger.info("In controller - {}", "Food deleted for food name: " +favFoodId);
			return new ResponseEntity<FavouriteFood>(HttpStatus.OK);
		}
		logger.info("In controller - {}", "Food not found for Food Name: " +favFoodId);
		return new ResponseEntity<FavouriteFood>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{favFoodId}")
	public ResponseEntity<FavouriteFood> updateFavouriteFood(@PathVariable("favFoodId") int favFoodId, @RequestBody FavouriteFood food) 
		{
		try {
			FavouriteFood foodUpdated = favFoodService.updateFavouriteFood(food, favFoodId);
				if(foodUpdated != null) {
				logger.info("In controller - {}", "Food updated for Food Name: " +favFoodId + " is: " +food);
				return new ResponseEntity<FavouriteFood>(foodUpdated, HttpStatus.OK);
			}
		} catch (FoodNotFoundException e) {
			logger.info("In controller - {}", "Food not found for and Food Name: " +favFoodId);
			return new ResponseEntity<FavouriteFood>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "Food not found for and Food Name: " +favFoodId);
		return new ResponseEntity<FavouriteFood>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{userId}/{favFoodId}")
	public ResponseEntity<FavouriteFood> getFavouriteFoodById(@PathVariable("userId") String userId, @PathVariable("favFoodId") int favFoodId){
		FavouriteFood foodByName;
		try {
			foodByName = favFoodService.getFavouriteFoodById(userId, favFoodId);
			if(foodByName != null) {
				logger.info("In controller - {}", "The Food for User ID: "+userId+ " and food Name: " +favFoodId+ " is: "+foodByName);
				return new ResponseEntity<FavouriteFood>(foodByName, HttpStatus.OK);
			}
		} catch (FoodNotFoundException e) {
			logger.info("In controller - {}", "Food Name "+favFoodId+ " not Found.");
			return new ResponseEntity<FavouriteFood>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "Food Name "+favFoodId+ " not Found.");
		return new ResponseEntity<FavouriteFood>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<FavouriteFood>> getAllFavouriteFoodByUserId(@PathVariable("userId") String userId){
		List<FavouriteFood> allFood = favFoodService.getAllFavouriteFoodByUserId(userId);
		if(allFood != null) {
			logger.info("In controller - {}", "List of all news: "+allFood);
			return new ResponseEntity<List<FavouriteFood>>(allFood, HttpStatus.OK);
		}
		else {
			logger.info("In controller - {}", "User ID "+userId+ " not Found.");
			return new ResponseEntity<List<FavouriteFood>>(HttpStatus.NOT_FOUND);
		}
	}

}
