package com.hotel.services;

import java.util.List;

import com.hotel.entities.Hotel;

public interface HotelService {

	//create
	Hotel create(Hotel hotel);
	
	//getall
	List<Hotel> getAll();
	
	//get single
	Hotel get(String id);
}
