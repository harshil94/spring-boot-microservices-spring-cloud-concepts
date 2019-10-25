package com.learn.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.learn.microservice.model.Movie;
import com.learn.microservice.model.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieDetailsController {
	
	@Value("${api.key}")
	private String apiKey;
	
	@Value("${api.baseUrl}")
	private String baseUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) throws InterruptedException{
		MovieSummary movieSummary = 
				restTemplate.getForObject(baseUrl+movieId+"?api_key="+apiKey, MovieSummary.class);
		
		return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
	}
}
