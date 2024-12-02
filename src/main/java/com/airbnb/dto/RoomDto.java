package com.airbnb.dto;

import com.airbnb.entity.Property;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomDto {
    private Long id;
    private String typeOfRoom;
    private Float price;
    private Integer count;
    private Property property;
    private LocalDate date;
}
