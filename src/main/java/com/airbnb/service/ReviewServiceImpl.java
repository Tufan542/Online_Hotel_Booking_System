package com.airbnb.service;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import com.airbnb.exception.ResourceNotFound;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }


    @Override
    public ReviewDto createReview(ReviewDto reviewDto, AppUser user, long propertyId) throws ResourceNotFound {
        Property property = propertyRepository.findById(propertyId).get();
        Review reviewDetails = reviewRepository.findByUserAndProperty(user,property);
        if(reviewDetails != null)
        {
            throw new ResourceNotFound("Review already exists");
        }
        reviewDto.setAppUser(user);
        reviewDto.setProperty(property);
        Review savedreview = mapToEntity(reviewDto);
        Review save = reviewRepository.save(savedreview);
        ReviewDto reviewDto1 = mapToDto(save);
        return reviewDto1;
    }

    @Override
    public List<ReviewDto> getListOfReviewsOfUser(AppUser user) {

            List<Review> reviewsByUser = reviewRepository.findReviewsByUser(user);
        List<ReviewDto> reviewDtos = reviewsByUser.stream()
                .map(review -> mapToDto(review))
                .collect(Collectors.toList());
        return reviewDtos;
    }

    Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setDescription(reviewDto.getDescription());
        review.setAppUser(reviewDto.getAppUser());
        review.setProperty(reviewDto.getProperty());
        return review;
    }

        ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setDescription(review.getDescription());
        reviewDto.setAppUser(review.getAppUser());
        reviewDto.setProperty(review.getProperty());
        return reviewDto;
        }
}
