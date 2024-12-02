package com.airbnb.service;

import com.airbnb.dto.CityDto;
import com.airbnb.entity.City;
import com.airbnb.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityServiceImpl implements CityService{
    private CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityDto createCity(CityDto cityDto) {
         City city = mapToEntity(cityDto);
        City savedCity = cityRepository.save(city);
        CityDto dto = mapToDto(savedCity);
        return dto;
    }

    @Override
    public City getCityById(long cityId) {
        City city = cityRepository.findById(cityId).get();
        return city;
    }

    City mapToEntity(CityDto cityDto)
    {
        City city= new City();;
        city.setName(cityDto.getName());
        return city;
    }

    CityDto mapToDto(City city)
    {
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        return cityDto;
    }
}
