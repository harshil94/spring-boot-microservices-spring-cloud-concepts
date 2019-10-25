package com.learn.microservice.model;

import java.util.List;

public class UserRating {
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private List<Rating> userRatings;
	
	public List<Rating> getUserRatings() {
		return userRatings;
	}
	public void setUserRatings(List<Rating> userRatings) {
		this.userRatings = userRatings;
	}
}
