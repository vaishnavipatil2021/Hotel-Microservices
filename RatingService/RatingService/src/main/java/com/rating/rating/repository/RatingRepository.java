package com.rating.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rating.rating.entities.Rating;


public interface RatingRepository extends JpaRepository<Rating, String> {

	//custom finder methods
	List<Rating> findByUserId(String userId);
	
	List<Rating> findByHotelId(String hotelId);
}
