package com.airbnb.service;

import com.airbnb.dto.CountryDto;
import com.airbnb.entity.Country;
import com.airbnb.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService{
    private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    public CountryDto createCountry(CountryDto countrydto) {
        Country country = mapToEntity(countrydto);
        Country savedCountry = countryRepository.save(country);
        CountryDto countryDto = mapToDto(savedCountry);
        return countryDto;
    }

    @Override
    public Country getCountryById(long countryId) {
        Country country = countryRepository.findById(countryId).get();
        return country;
    }

    Country mapToEntity(CountryDto countrydto)
    {
        Country country = new Country();
        country.setName(countrydto.getName());
        return  country;
    }

    CountryDto mapToDto(Country country)
    {
        CountryDto dto = new CountryDto();
        dto.setId(country.getId());
        dto.setName(country.getName());
        return dto;
    }
}
