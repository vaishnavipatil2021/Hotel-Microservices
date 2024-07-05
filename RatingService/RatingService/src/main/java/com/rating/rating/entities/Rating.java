package com.rating.rating.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name="user_rating")
@Entity
public class Rating {

	@Id
	public String ratingId;
	private String userId;
	private String hotelId;
	private int rating;
	private String feedback;
	
	
}
