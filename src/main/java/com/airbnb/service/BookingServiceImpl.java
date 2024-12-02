package com.airbnb.service;

import com.airbnb.config.ModelMapperConfig;
import com.airbnb.dto.BookingDto;
import com.airbnb.entity.AppUser;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.Room;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private ModelMapper modelMapper;

    @Override
    public BookingDto createBookings(BookingDto bookingDto) {
        Booking map = modelMapper.map(bookingDto, Booking.class);

        Booking savedBooking = bookingRepository.save(map);
        BookingDto map1 = modelMapper.map(savedBooking, BookingDto.class);
        return map1;
    }
    public  List<LocalDate> getDatesBetween(LocalDate startDate , LocalDate endDate){
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while(!currentDate.isAfter(endDate)){
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }

    @Override
    public List<Booking> createBulkBooking(Property property, List<BookingDto> bookingsDto, AppUser user) throws Exception {
        List<Booking> bookingList = new ArrayList<>();
        for(BookingDto dto : bookingsDto)
        {
            Booking booking = modelMapper.map(dto, Booking.class);
            booking.setProperty(property);
            booking.setAppUser(user);
            List<LocalDate> datesBetween = getDatesBetween(booking.getCheckInDate(),booking.getCheckOutDate());
            String typeOfRoom = booking.getTypeOfRoom();
            List<Room> rooms = new ArrayList<>();
            for(LocalDate date:datesBetween)
            {
                Room room = roomRepository.findByPropertyIdAndTypeAndDate(property.getId(), typeOfRoom, date);
                if(room != null || room.getCount()==0)
                {
                    throw  new Exception("No rooms available for the selected dates");
                }
                rooms.add(room);
            }

            float total = rooms.stream().map(Room::getPrice).reduce(0f, Float::sum);
            // Reduces the stream of prices to a single Float value by summing up all the prices. The initial value is 0f.
            booking.setTotal_price(total);
            Booking savedBooking = bookingRepository.save(booking);
            for(Room room :  rooms)
            {
                room.setCount(room.getCount()-1);
                roomRepository.save(room);
            }
            bookingList.add(savedBooking);
        }
        return bookingList;
    }


    @Override
    @Transactional
    public void checkOutBooking(long bookingId)  {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        List<LocalDate> datesBetween = getDatesBetween(booking.getCheckInDate(), booking.getCheckOutDate());
        String typeOfRoom = booking.getTypeOfRoom();
        Long propertyId = booking.getProperty().getId();
        LocalDate today = LocalDate.now();
        for (LocalDate date : datesBetween) {
            if(date.isAfter(today)){
            Room room = roomRepository.findByPropertyIdAndTypeAndDate(propertyId, typeOfRoom, date);
            if (room != null) {
                room.setCount(room.getCount() + 1);
                roomRepository.save(room);
            }
            }
        }

        booking.setCheckedOut(true); // Assumes you have this field in your Booking entity
        bookingRepository.save(booking);
    }


}
