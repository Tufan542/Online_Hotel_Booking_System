package com.airbnb.service;

import com.airbnb.dto.CityDto;
import com.airbnb.entity.City;

public interface CityService {
    public CityDto createCity(CityDto cityDto);

    City getCityById(long cityId);
}
