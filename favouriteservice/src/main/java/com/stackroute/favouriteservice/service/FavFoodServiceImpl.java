package com.stackroute.favouriteservice.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.favouriteservice.exception.FoodNotFoundException;
import com.stackroute.favouriteservice.model.FavouriteFood;
import com.stackroute.favouriteservice.repository.FavFoodServiceRepository;

@Service
public class FavFoodServiceImpl implements FavFoodService {
	
	private FavFoodServiceRepository favFoodServiceRepo;
	
	public FavFoodServiceImpl() {
	}
	
	@Autowired
	public FavFoodServiceImpl(FavFoodServiceRepository foodServiceRepository) {
		this.favFoodServiceRepo = foodServiceRepository;
	}

	@Override
	public boolean addFavouriteFood(FavouriteFood favFood) {
		Boolean favFoodById = favFoodServiceRepo.existsById(favFood.getFoodId());
		if(favFoodById == false) {
			FavouriteFood favFoodAdded = favFoodServiceRepo.insert(favFood);
			if(favFoodAdded != null)
				return true;
		}
		return false;
	}

	/* This method should be used to delete an existing favFood. */

	@Override
	public boolean deleteFavouriteFood(int favFoodId) {
		if(favFoodServiceRepo.findById(favFoodId) != null) {
			favFoodServiceRepo.deleteById(favFoodId);
			return true;
		}
		return false;
	}

	/* This method should be used to update an existing favFood. */
	
	@Override
	public FavouriteFood updateFavouriteFood(FavouriteFood favFood, int favFoodId) throws FoodNotFoundException {
		FavouriteFood favFoodToUpdate = favFoodServiceRepo.findById(favFoodId).get();
		if(favFoodToUpdate != null) {
			favFoodToUpdate.setFavFoodName(favFood.getFavFoodName());
			favFoodToUpdate.setFavFoodCreatedBy(favFood.getFavFoodCreatedBy());
			favFoodToUpdate.setFavFoodDesc(favFood.getFavFoodDesc());
			favFoodServiceRepo.save(favFoodToUpdate);
			return favFoodToUpdate;
		}
		throw new FoodNotFoundException("Fav Food not Found in DB.");
	}

	/* This method should be used to get a specific favFood for an user. */

	@Override
	public FavouriteFood getFavouriteFoodById(String userId, int favFoodId) throws FoodNotFoundException {
		try {
			List<FavouriteFood> favFoodList = favFoodServiceRepo.findAllFavFoodByFavFoodCreatedBy(userId);
			if(!favFoodList.isEmpty()) {
				for(FavouriteFood favFood: favFoodList) {
					if(favFood.getFoodId() == favFoodId) {
						return favFood;
					}
				}
			}
		}
		catch(NoSuchElementException ex) {
			return null;
		}
		throw new FoodNotFoundException("Fav Food not Found in DB.");
	}
	
	 /* This method should be used to get all favFood for a specific userId.*/

	@Override
	public List<FavouriteFood> getAllFavouriteFoodByUserId(String createdBy) {
		return favFoodServiceRepo.findAllFavFoodByFavFoodCreatedBy(createdBy);
	}
}