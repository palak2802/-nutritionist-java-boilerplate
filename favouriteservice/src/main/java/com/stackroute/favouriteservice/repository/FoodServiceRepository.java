package com.stackroute.favouriteservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.favouriteservice.model.FavouriteFood;

@Repository
public interface FoodServiceRepository extends MongoRepository<FavouriteFood, String> {

}

