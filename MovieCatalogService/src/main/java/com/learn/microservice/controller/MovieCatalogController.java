package com.learn.microservice.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learn.microservice.model.CatalogItem;
import com.learn.microservice.model.UserRating;
import com.learn.microservice.services.MovieDetails;
import com.learn.microservice.services.MovieRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
	
	@Autowired
	MovieDetails movieDetails;
	
	@Autowired
	MovieRating movieRating;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		//get all rated movie IDs
		UserRating userRating = movieRating.getUserRating(userId);
		
		//For each movie Id, call movie info service and get details
		return userRating.getUserRatings().stream().map(rating -> {
			return movieDetails.getCatalogItem(rating);
		}).collect(Collectors.toList());
	}
	
	
	
}
