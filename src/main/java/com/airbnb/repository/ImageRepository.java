package com.airbnb.repository;

import com.airbnb.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
   List<Image> findByPropertyId(String propertyId);
}