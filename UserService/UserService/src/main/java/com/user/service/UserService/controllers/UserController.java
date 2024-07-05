package com.user.service.UserService.controllers;

//import org.hibernate.mapping.List;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.UserService.entities.User;
import com.user.service.UserService.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User userSaved = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
	}
	
	int retryCount=1;
	@GetMapping("/{userId}")
	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
	//@Retry(name="ratingHotelService",fallbackMethod="ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
	
	//single user get
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		System.out.println("Retry count:{}"+retryCount);
		retryCount++;
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	
	public ResponseEntity<User> ratingHotelFallback(String userId,Throwable  ex){
		//System.out.println("FallBack is called" + ex.getMessage());
		
		User user = new User();
		user.setEmail("dummy@gmail.com");
		user.setName("dummy");
		user.setAbout("this is userid");
		user.setUserId("1465");
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	
	@GetMapping  //("/users")
	//all user get
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}
}

