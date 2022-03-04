package com.stackroute.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.authenticationservice.model.User;

/*
* This class is implementing the JPARepository interface for User.
* Annotate this class with @Repository annotation
* */

@Repository
public interface UserAuthRepository extends JpaRepository<User, String> {
	
	public User findByUserIdAndPassword(String userId, String password);
}

