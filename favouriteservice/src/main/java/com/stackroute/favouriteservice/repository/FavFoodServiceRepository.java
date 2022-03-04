package com.stackroute.favouriteservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.favouriteservice.model.FavouriteFood;

@Repository
public interface FavFoodServiceRepository extends MongoRepository<FavouriteFood, Integer> {
	
	List<FavouriteFood> findAllFavFoodByFavFoodCreatedBy(String userId);

}

