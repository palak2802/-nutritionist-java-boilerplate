package com.stackroute.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.authenticationservice.exception.UserAlreadyExistsException;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.model.User;
import com.stackroute.authenticationservice.repository.UserAuthRepository;

import java.util.Optional;


   /*
	* Service classes are used here to implement additional business logic/validation 
	* This class has to be annotated with @Service annotation.
	* @Service - It is a specialization of the component annotation. It doesn't currently 
	* provide any additional behavior over the @Component annotation, but it's a good idea 
	* to use @Service over @Component in service-layer classes because it specifies intent 
	* better. Additionally, tool support and additional behavior might rely on it in the 
	* future.
	*/
@Service
public class UserAuthServiceImpl implements UserAuthService {

	private UserAuthRepository userAuthRepo;
	@Autowired
	public UserAuthServiceImpl(UserAuthRepository userAuthRepo) {
		super();
		this.userAuthRepo = userAuthRepo;
	}

	/*
	 * This method should be used to find an existing User with correct password.
	 */
    
    @Override
    public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {
    	User user = userAuthRepo.findByUserIdAndPassword(userId, password);
		if(user != null)
			return user;
		return null;
    }

	/*
	 * This method should be used to save a new User.
	 */
    
    @Override
    public boolean saveUser(User user) throws UserAlreadyExistsException {
    	Optional<User> isUserExists = userAuthRepo.findById(user.getUserId());
    	if(isUserExists.isEmpty()) {
    		userAuthRepo.save(user);
    		return true;
    	}
    	else{
    		throw new UserAlreadyExistsException("User Already Exists.");
    	}
    }
}
