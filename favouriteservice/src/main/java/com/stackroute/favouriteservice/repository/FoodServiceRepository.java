package com.stackroute.favouriteservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.favouriteservice.model.UserFavFood;

@Repository
public interface FoodServiceRepository extends MongoRepository<UserFavFood, String> {
	
}

