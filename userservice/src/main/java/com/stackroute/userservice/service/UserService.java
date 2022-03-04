package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;

public interface UserService {
	
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	User registerUser(User user) throws UserAlreadyExistsException;

	User updateUser(String userId,User user) throws UserNotFoundException;

	boolean deleteUser(String userId) throws UserNotFoundException;

	User getUserById(String userId) throws UserNotFoundException;

}
