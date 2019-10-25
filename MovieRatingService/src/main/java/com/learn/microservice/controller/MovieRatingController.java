package com.learn.microservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.microservice.model.Rating;
import com.learn.microservice.model.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class MovieRatingController {
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId){
		return new Rating(movieId,4);
	}
	
	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId){
		List<Rating> userRatings = Arrays.asList(
			new Rating("100", 4),
			new Rating("500", 3)
		);
		UserRating userRating = new UserRating();
		userRating.setUserRatings(userRatings);
		return userRating;
	}
}
