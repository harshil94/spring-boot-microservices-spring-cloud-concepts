package com.learn.microservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.learn.microservice.model.Rating;
import com.learn.microservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieRating {
	
	@Autowired
	RestTemplate restTemplate;
	
	//Implemented The Bulkhead Pattern (example of containerized ship --> Different thread pools for different service calls)
	@HystrixCommand(fallbackMethod="getFallbackUserRating",
			threadPoolKey="movieRatingPool",
			threadPoolProperties={
				//concurrent threads allowed at a time
				@HystrixProperty(name="coreSize",value="20"),
				
				//number of requests that can be queued if all threads are occupied
				@HystrixProperty(name="maxQueueSize",value="10")
			})
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://movie-rating-service/ratingsdata/users/"+userId,
				UserRating.class);
	}
	
	public UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRatings(Arrays.asList(
			new Rating("0",0)
		));
		return userRating;
	}
}
