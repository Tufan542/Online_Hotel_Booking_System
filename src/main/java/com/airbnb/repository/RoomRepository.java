package com.airbnb.repository;

import com.airbnb.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface RoomRepository extends JpaRepository<Room, Long> {
//    @Query("SELECT r from Room r WHERE r.property.id=:propertyId AND r.typeOfRoom=:roomtype")
//   Room findByPropertyIdAndTypeOfRoom(@Param("propertyId")Long propertyId,@Param("roomtype") String roomtype );

    @Query("SELECT r FROM Room r WHERE r.property.id=:propertyId AND r.typeOfRoom=:type AND r.date=:date")
    Room findByPropertyIdAndTypeAndDate(
            @Param("propertyId")Long propertyId,
            @Param("type")String type,
            @Param("date")LocalDate date
            );

}