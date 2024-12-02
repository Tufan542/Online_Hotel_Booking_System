package com.airbnb.repository;

import com.airbnb.entity.Property;
import com.airbnb.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property,Long> {

//    @Query("SELECT p FROM Property p JOIN City c ON p.city.id = c.id WHERE c.name = :name")
//    List<Property> searchProperty(
//            @Param("name") String cityName);
//        @Query("select p from Property p INNER JOIN  p.city c where c.name=:name ")
//        List<Property> searchProperty(
//                @Param("name") String cityName);
        @Query("select p from Property p INNER JOIN  p.city c INNER JOIN p.country co where c.name=:name or co.name=:name")
        List<Property> searchProperty(
               @Param("name") String name);

        void deleteById(long id);
        Optional<Property> deleteByName(String name);
}
