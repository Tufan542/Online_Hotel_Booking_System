package com.airbnb.service;

import com.airbnb.dto.RoomDto;
import com.airbnb.entity.Room;

import java.util.List;

public interface RoomService {

    RoomDto createRoomDetails(RoomDto roomDto);

    void deleteRoomById(long id);

    List<Room> addRoomInBulk(List<Room> rooms);
}
