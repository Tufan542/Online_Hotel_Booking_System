package com.airbnb.controller;

import com.airbnb.dto.RoomDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.Room;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomRepository;
import com.airbnb.service.PropertyService;
import com.airbnb.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private RoomService roomService;
    private PropertyService propertyService;
    private final RoomRepository roomRepository;
    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;


    @PostMapping("/createRoom")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto, @RequestParam long propertyid)
    {
        Property property = propertyService.getById(propertyid);
        roomDto.setProperty(property);
        RoomDto roomDetails = roomService.createRoomDetails(roomDto);
        return new ResponseEntity<>(roomDetails, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteRoom")
    public ResponseEntity<String> deleteRoomById(@RequestParam long id)
    {
        roomService.deleteRoomById(id);
        return new ResponseEntity<>("Successfully Room Deleted By Id",HttpStatus.OK);
    }

    @PostMapping("/bulkadd")
    public ResponseEntity<?> addRoomInBulk(@RequestBody List<Room> rooms)
    {
        if(rooms.isEmpty())
        {
            return new ResponseEntity<>("No room provided",HttpStatus.BAD_REQUEST);
        }
        try{
            List<Room> roomslist = roomService.addRoomInBulk(rooms);
            return new ResponseEntity<>("Successfully added rooms in bulk",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while saving rooms", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
