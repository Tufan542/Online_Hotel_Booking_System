package com.airbnb.dto;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class PropertyDto {
    private Long id;
    private String name;
    private Integer numberOfGuests;
    private Integer numberOfBeds;
    private Integer numberOfBathrooms;
    private Integer numberOfBedrooms;
    private Country country;
    private City city;
}
