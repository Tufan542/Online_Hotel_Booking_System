package com.airbnb.dto;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
public class BookingDto {
    private Long id;
    private Integer noOfGuests;
    private String guestName;
    private String mobile;
    private String email;
    private Property property;
    private AppUser appUser;
    private Float total_price;
    private String typeOfRoom;
    private boolean checkedOut;
    private Integer totalNights;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
