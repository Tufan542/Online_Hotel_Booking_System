package com.airbnb.service;

import com.airbnb.dto.CountryDto;
import com.airbnb.entity.Country;

public interface CountryService {
    public CountryDto createCountry(CountryDto countrydto);

    Country getCountryById(long countryId);
}
