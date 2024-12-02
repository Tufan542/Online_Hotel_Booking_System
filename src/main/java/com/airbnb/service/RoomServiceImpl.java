package com.airbnb.service;

import com.airbnb.dto.RoomDto;
import com.airbnb.entity.Room;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    private ModelMapper modelMapper;
    private PropertyRepository propertyRepository;
    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper, PropertyRepository propertyRepository) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public RoomDto createRoomDetails(RoomDto roomDto) {
     //   Property property = propertyRepository.findById(propertyid).get();
        Room room = modelMapper.map(roomDto,Room.class);
        Room save = roomRepository.save(room);
//        room.setProperty(property);
        RoomDto dto =modelMapper.map(room,RoomDto.class);
        return dto;
    }

    @Override
    public void deleteRoomById(long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> addRoomInBulk(List<Room> rooms) {
        return roomRepository.saveAll(rooms);
    }


}
