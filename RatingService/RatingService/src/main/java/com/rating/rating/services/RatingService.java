package com.rating.rating.services;

import java.util.List;

import com.rating.rating.entities.Rating;

public interface RatingService {

	//create
	Rating createRating(Rating rating);

	//get all ratings
	List<Rating> getAllRatings();

	//get all by userId
	List<Rating> getRatingByUserId(String userId);

	//get all by hotel
	List<Rating> getRatingByHotelId(String hotelId);
}
