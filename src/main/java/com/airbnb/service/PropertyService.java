package com.airbnb.service;

import com.airbnb.dto.PropertyDto;
import com.airbnb.dto.PropSortDto;
import com.airbnb.entity.Property;

import java.util.List;

public interface PropertyService {
    public PropertyDto createProperty(PropertyDto propertyDto);

    List<PropertyDto> searchProperty(String cityName);

    void deleteById(long id);

    public void deleteByPropertyName(String name);
    PropertyDto updateById(PropertyDto propertyDto, long id);

    PropertyDto getAllPropertyById(long id);

    PropSortDto<PropertyDto> getProperty(int pageNo, int pageSize, String sortBy, String sortDir);

    Property getById(long propertyid);
}
