package com.airbnb.repository;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.appUser = :user AND r.property = :property")
    Review findByUserAndProperty(@Param("user") AppUser user, @Param("property") Property property);

    @Query("SELECT r FROM Review r WHERE r.appUser = :user")
    List<Review> findReviewsByUser(@Param("user") AppUser user);
}
