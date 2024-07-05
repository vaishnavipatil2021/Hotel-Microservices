package com.user.service.UserService.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.UserService.entities.Hotel;
import com.user.service.UserService.entities.Rating;
import com.user.service.UserService.entities.User;
import com.user.service.UserService.exceptions.ResourceNotFoundException;
import com.user.service.UserService.repositories.UserRepository;
import com.user.service.UserService.services.UserService;

import feignclientproxy.HotelServiceProxy;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelServiceProxy hotelServiceProxy;
	
	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		//generate unique userid
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		
		//get user from database with help of user repo
		//return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id is not found on server!!:" + userId));
		 User user= userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
		 //fetch rating of this user from RATING SERVICE
		 //http://localhost:8083/ratings/users/aa9865c9-0417-4528-9736-6d306e418266
		 //http API call
		 
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		 logger.info("{}",ratingsOfUser);
		 
		 List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
	        List<Rating> ratingList = ratings.stream().map(rating -> {
	        	
	            //api call to hotel service to get the hotel
	            //http://localhost:8082/hotels/1cbaf36d-0b28-4173-b5ea-f1cb0bc0a791
	        	
	            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
	            Hotel hotel = hotelServiceProxy.getHotel(rating.getHotelId());
	             //logger.info("response status code: {} ",forEntity.getStatusCode());
	             
	            //set the hotel to rating
	            rating.setHotel(hotel);
	            //return the rating
	            return rating;
	        }).collect(Collectors.toList());
		 
		 user.setRatings(ratingList);
		 return user;
	}


}
