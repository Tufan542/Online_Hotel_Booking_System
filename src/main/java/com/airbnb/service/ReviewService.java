package com.airbnb.service;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Review;
import com.airbnb.exception.ResourceNotFound;

import java.util.List;

public interface ReviewService {

    public ReviewDto createReview(ReviewDto reviewDto, AppUser user,long propertyId) throws ResourceNotFound;

    List<ReviewDto> getListOfReviewsOfUser(AppUser user);
}
