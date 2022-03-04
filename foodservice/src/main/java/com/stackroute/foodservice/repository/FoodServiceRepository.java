package com.stackroute.foodservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.foodservice.model.Food;
import com.stackroute.foodservice.model.UserFavFood;

@Repository
public interface FoodServiceRepository extends MongoRepository<UserFavFood, String> {
	
	Food findFoodByFoodName(String foodName);
	
	List<Food> findAllFoods();
	
	Food saveFood(Food food);

}

