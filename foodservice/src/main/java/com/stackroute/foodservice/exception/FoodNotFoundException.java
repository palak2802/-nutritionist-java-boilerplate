package com.stackroute.foodservice.exception;

public class FoodNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public FoodNotFoundException(String message) {
		super(message);
	}
}
