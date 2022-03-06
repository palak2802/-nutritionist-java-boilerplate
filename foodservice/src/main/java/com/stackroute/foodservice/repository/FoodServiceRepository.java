package com.stackroute.foodservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.foodservice.model.UserFavFood;

@Repository
public interface FoodServiceRepository extends MongoRepository<UserFavFood, String> {
	
}

