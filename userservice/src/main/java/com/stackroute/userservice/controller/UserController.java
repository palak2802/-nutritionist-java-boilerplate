package com.stackroute.userservice.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    /*
	 * Autowiring should be implemented for the UserAuthService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	static final long EXPIRATIONTIME = 300000;
	private Map<String, String> map = new HashMap<>();
	@Autowired
	private UserService userService;
	
    public UserController(UserService userService) {
    	this.userService = userService;
	}
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the user created successfully. 
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user, 
	 * UserAlreadyExistsException is caught.
	 * 
	 * This handler method should map to the URL "/api/v1/auth/register" using HTTP POST method
	 */
    @PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) throws UserNotFoundException{
    	try {
    		User userById = userService.findByUserIdAndPassword(user.getUserId(), user.getPassword());
    		if(userById == null) {
    			userService.saveUser(user);
    			logger.info("In controller - {}", "User is registered successfully.");
    			return new ResponseEntity<User>(user, HttpStatus.CREATED);
    		}
    	}catch(UserAlreadyExistsException e) {
    		logger.info("In controller - {}", "User already exists.");
    		return new ResponseEntity<User>(HttpStatus.CONFLICT);
    	} 
    	logger.info("In controller - {}", "User already exists.");
    	return new ResponseEntity<User>(HttpStatus.CONFLICT);
	}

	/* 
	 * Define a handler method which will authenticate a user by reading the Serialized user
	 * object from request body containing the username and password. The username and password should be validated 
	 * before proceeding ahead with JWT token generation. The user credentials will be validated against the database entries. 
	 * The error should be return if validation is not successful. If credentials are validated successfully, then JWT
	 * token will be generated. The token should be returned back to the caller along with the API response.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 401(UNAUTHORIZED) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP POST method
	*/
    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) throws ServletException{
    	String jwtToken = "";
    	try {
    		jwtToken = getToken(user.getUserId(), user.getPassword());
    		map.clear();
    		map.put("message", "user successfully logged in");
    		map.put("token", jwtToken);
    	}
    	catch(Exception e) {
    		String exceptionMsg = e.getMessage();
    		map.clear();
    		map.put("token", null);
    		map.put("message", exceptionMsg);
    		logger.info("In controller - {}", "Unauthorized User.");
    		return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    	}
    	logger.info("In controller - {}", "Authorized User.");
    	return new ResponseEntity<>(map, HttpStatus.OK);
	}
    
    public String getToken(String userName, String password) throws Exception{
    	if(userName == null || password == null) {
    		throw new ServletException("Please fill in username and password.");
    	}
    	
    	User isUserExists = userService.findByUserIdAndPassword(userName, password);
    	
    	if(isUserExists == null ) {
    		throw new ServletException("Invalid Credentials.");
    	}
    	
    	String jwtToken = Jwts.builder().setSubject(userName)
    			.setIssuedAt(new Date())
    			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
    			.signWith(SignatureAlgorithm.HS256, "secretkey")
    			.compact();
    	
    	logger.info("In controller - {}", "JWT Token created Successfully.");
    	return jwtToken;
    }
}
