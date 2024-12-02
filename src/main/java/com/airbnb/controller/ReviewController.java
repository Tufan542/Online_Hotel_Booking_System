package com.airbnb.controller;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Review;
import com.airbnb.exception.ResourceNotFound;
import com.airbnb.service.ReviewService;
import com.airbnb.service.ReviewServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/createreview")
    public ResponseEntity<ReviewDto> createReview(
            @RequestBody ReviewDto reviewDto,
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId
            ) throws ResourceNotFound {
//         System.out.println(user.getName());
//        System.out.println(user.getEmail());
        ReviewDto review = reviewService.createReview(reviewDto, user, propertyId);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/userReviews")
    public ResponseEntity<List<ReviewDto>> listReviewsOfUser(
            @AuthenticationPrincipal AppUser user
    )
    {
        List<ReviewDto> listOfReviewsOfUser = reviewService.getListOfReviewsOfUser(user);
        return new ResponseEntity<>(listOfReviewsOfUser, HttpStatus.OK);
    }
}
// @AuthenticationPrincipal - it does the movement you login,after logging in you will call this review link with the jwt token user details will automatically go to appuser object
//this will automaticcally track logged in user
