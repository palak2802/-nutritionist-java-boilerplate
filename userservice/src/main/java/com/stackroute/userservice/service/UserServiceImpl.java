package com.stackroute.userservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.repository.UserRepository;

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
public class UserServiceImpl implements UserService {

	private UserRepository userRepo;
	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	public User registerUser(User user) throws UserAlreadyExistsException {
		Boolean userProfileById = userRepo.existsById(user.getUserId());
    	if(userProfileById == false) {
    		User userProfile = userRepo.insert(user);
    		if(userProfile != null)
    			return userProfile;
    	}
        throw new UserAlreadyExistsException("User Profile with user ID: "+user.getUserId()+ " already exists in DB.");
    }

	/*
	 * This method should be used to update a existing userprofile.Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public User updateUser(String userId, User user) throws UserNotFoundException {
    	User userProfileById = userRepo.findById(userId).get();
    	if(userProfileById != null) {
    		userProfileById.setFirstName(user.getFirstName());
    		userProfileById.setLastName(user.getLastName());
    		userProfileById.setContact(user.getContact());
    		userProfileById.setEmail(user.getEmail());
    		userRepo.save(userProfileById);
    		User userProfile = userRepo.findById(userId).get();
    		return userProfile;
    	}
        throw new UserNotFoundException("User Profile with user ID: "+userId+ " does not found in DB.");
    }

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public boolean deleteUser(String userId) throws UserNotFoundException {
    	User userProfileById = userRepo.findById(userId).get();
    	if(userProfileById != null) {
    		userRepo.deleteById(userId);
    		return true;
    	}
        throw new UserNotFoundException("User Profile with user ID: "+userId+ " does not found in DB.");
    }
    
	/*
	 * This method should be used to get userprofile by userId.Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
    	Optional<User> userProfileById = userRepo.findById(userId);
    	if(!userProfileById.isEmpty()) {
    		return userProfileById.get();
    	}
        throw new UserNotFoundException("User Profile with user ID: "+userId+ " does not found in DB.");
    }
}
