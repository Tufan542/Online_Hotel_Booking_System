package com.airbnb.controller;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.Room;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomRepository;
import com.airbnb.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingService bookingService;
    private PropertyService propertyService;
    private PropertyRepository propertyRepository;
    private RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
   // private PDFService pdfService;
    //private SmsService smsService;
 //   private WhatsAppService whatsAppService;

    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(
            @RequestParam long propertyId,
            @RequestParam String roomType,
            @RequestBody Booking booking,
            @AuthenticationPrincipal AppUser user
    )
    {
        Property property = propertyRepository.findById(propertyId).get();
        List<LocalDate> datesBetween = bookingService.getDatesBetween(booking.getCheckInDate(), booking.getCheckOutDate());
        List<Room> rooms = new ArrayList<>();
        for(LocalDate date : datesBetween) {
            Room room = roomRepository.findByPropertyIdAndTypeAndDate(propertyId, roomType, date);
           // rooms.clear();// Clears the list of rooms
            if (room.getCount() == 0) {
                return new ResponseEntity<>("No rooms available for the selected dates", HttpStatus.BAD_REQUEST);
            }
            rooms.add(room);// Add the room to the list if it is available
        }
        //booking price
        // Calculate total price, excluding the last day (check-out date)
        LocalDate checkOutDate = booking.getCheckOutDate();
        float total = 0;
        for (LocalDate date : datesBetween) {
            if (!date.equals(checkOutDate)) {
                Room room = roomRepository.findByPropertyIdAndTypeAndDate(propertyId, roomType, date);
                if (room != null) {
                    total =total+ room.getPrice();
                }
            }
        }
       // System.out.println(total);
        booking.setTotal_price(total);
        booking.setProperty(property);
        booking.setAppUser(user);
        booking.setTypeOfRoom(roomType);
        Booking savedbooking = bookingRepository.save(booking);
        //adding bulk data to data base using spring boot rest api
        if(savedbooking != null) {
            for (Room room : rooms) {
                Integer availableRooms = room.getCount();
                room.setCount(availableRooms - 1);
                roomRepository.save(room);// Save the updated room count
            }
        }
        //generate pdf document
 //       pdfService.generatePDF(savedbooking);
        //generate sms confirmation
 //       smsService.sendSms("+91"+booking.getMobile(),"Your booking is confirmed with booiking Id is : "+ booking.getId());
//        // Generate WhatsApp confirmation with URL
//        String messageBody = "Your booking is confirmed with booking Id: " + booking.getId();
//        String toWhatsAppNumber = "+91" + booking.getMobile(); // Ensure this is a valid number
//        whatsAppService.sendWhatsAppMessage(toWhatsAppNumber, messageBody);
        return new ResponseEntity<>(savedbooking,HttpStatus.CREATED);
    }
    @PostMapping("/checkout")
    public ResponseEntity<?> checkOutBooking(@RequestParam long bookingId) {
        try {
            bookingService.checkOutBooking(bookingId);
            return new ResponseEntity<>("Successfully checked out and updated room availability", HttpStatus.OK);
        }  catch (Exception e) {
            return new ResponseEntity<>("An error occurred during checkout", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bulkBooking")
    @Transactional
    public ResponseEntity<?> bulkBooking(
            @RequestParam long propertyId,
            @RequestBody List<BookingDto> bookingsDto,
            @AuthenticationPrincipal AppUser user
    ) throws Exception {
        Property property = propertyService.getById(propertyId);
        if(property == null)
        {
            return  new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        }
        List<Booking> bulkBooking = bookingService.createBulkBooking(property, bookingsDto, user);
        return new ResponseEntity<>(bulkBooking, HttpStatus.CREATED);
    }
}
