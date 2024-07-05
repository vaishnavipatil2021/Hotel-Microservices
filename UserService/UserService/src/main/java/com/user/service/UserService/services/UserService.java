package com.user.service.UserService.services;

import java.util.List;

import com.user.service.UserService.entities.User;

public interface UserService {

	//user operations
	
		//create user
		User saveUser(User user);
		
		//get all users
		List<User> getAllUser();
		
		//get single user of given userID
		User getUser(String userId);
}
