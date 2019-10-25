package com.learn.microservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.learn.microservice.model.CatalogItem;
import com.learn.microservice.model.Movie;
import com.learn.microservice.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieDetails {
	
	@Autowired
	RestTemplate restTemplate;

	//Implemented circuit breaker pattern (example of circuit breaker in electric circuits)
	@HystrixCommand(fallbackMethod="getFallbackCatalogItem",
			commandProperties={
					//maximum time that a service can take to respond
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
					
					//number of last n requests to check in order to decide whether to break the circuit or not
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
					
					//break the circuit if the below % of requests are failed to execute from last n requests configured above
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
					
					//try to call the broken service again after below configured amount of time in order to check whether it has recovered or not
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000"),
			})
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-details-service/movies/"+rating.getMovieId(), Movie.class);
		//put all of them together
		return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found", "Movie description not found", rating.getRating());
	}
	
}
