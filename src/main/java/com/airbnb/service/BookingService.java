package com.airbnb.service;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    public BookingDto createBookings(BookingDto bookingDto);
    void checkOutBooking(long bookingId) throws EntityNotFoundException;
    List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate);

    List<Booking> createBulkBooking(Property property, List<BookingDto> bookingsDto, AppUser user) throws Exception;
}
