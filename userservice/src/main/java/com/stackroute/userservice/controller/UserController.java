package com.stackroute.userservice.controller;

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

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private UserService userService;
	
	@Autowired
    public UserController(UserService userService) {
		this.userService = userService;
    }
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/user")
	public ResponseEntity<User> registerUser(@RequestBody User user){
		try {
			User userProfileRegistered = userService.registerUser(user);
			if(userProfileRegistered != null) {
				logger.info("In controller - {}", "User created: " +user);
				return new ResponseEntity<User>(user, HttpStatus.CREATED);
			}
		} catch(UserAlreadyExistsException e) {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		logger.info("In controller - {}", "User ID "+ user.getUserId() + " already exists.");
		return new ResponseEntity<User>(HttpStatus.CONFLICT);
	}

	@PutMapping("/user/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @RequestBody User userProfile){
		try {
			User updatedProfile = userService.updateUser(userId, userProfile);
			if(updatedProfile != null) {
				logger.info("In controller - {}", "User updated: " +updatedProfile);
				return new ResponseEntity<User>(userProfile, HttpStatus.OK);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "User ID "+ userId + " not found in database.");
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable("userId") String userId){
		try {
			boolean isUserDeleted = userService.deleteUser(userId);
			if(isUserDeleted == true) {
				logger.info("In controller - {}", "User deleted for user ID: "+userId);
				return new ResponseEntity<User>(HttpStatus.OK);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "User ID "+ userId + " not found in database.");
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUser(@PathVariable("userId") String userId){
		try {
			User userProfileById = userService.getUserById(userId);
			if(userProfileById != null) {
				logger.info("In controller - {}", "User retrieved for user ID: "+userId+ "is: "+userProfileById);
				return new ResponseEntity<User>(userProfileById, HttpStatus.OK);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		logger.info("In controller - {}", "User ID "+ userId + " not found in database.");
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
}
