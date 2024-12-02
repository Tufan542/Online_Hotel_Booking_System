package com.airbnb.service;

import com.airbnb.dto.PropertyDto;
import com.airbnb.dto.PropSortDto;
import com.airbnb.entity.Property;
import com.airbnb.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService{
    private PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public PropertyDto createProperty(PropertyDto propertyDto) {
        Property property = mapToEntity(propertyDto);
        Property savedProperty = propertyRepository.save(property);
        PropertyDto savedPropertyDto = mapToDto(savedProperty);
        return savedPropertyDto;
    }

    @Override
    public List<PropertyDto> searchProperty(String cityName) {
        List<Property> properties = propertyRepository.searchProperty(cityName);
        List<PropertyDto> collect = properties.stream().map(property -> mapToDto(property)).collect(Collectors.toList());
        return collect;
    }

    @Override
   @Transactional
    public void deleteById(long id) {
        propertyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByPropertyName(String name) {
        propertyRepository.deleteByName(name);
    }
    @Override
    public PropertyDto updateById(PropertyDto propertyDto, long id) {
        Property property = propertyRepository.findById(id).get();
        property.setName(propertyDto.getName());
        property.setNumberOfGuests(property.getNumberOfGuests());
        property.setNumberOfBeds(propertyDto.getNumberOfBeds());
        property.setNumberOfBathrooms(propertyDto.getNumberOfBathrooms());
        property.setNumberOfBedrooms(propertyDto.getNumberOfBedrooms());
        property.setCity(propertyDto.getCity());
        property.setCountry(propertyDto.getCountry());
        Property save = propertyRepository.save(property);
        PropertyDto dto = mapToDto(save);
        return dto;
    }

    @Override
    public PropertyDto getAllPropertyById(long id) {
        Property property = propertyRepository.findById(id).get();
        PropertyDto dto = mapToDto(property);
        return dto;
    }

    @Override
    public PropSortDto<PropertyDto> getProperty(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(Sort.Direction.ASC,sortBy) : Sort.by(Sort.Direction.DESC,sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Property> all = propertyRepository.findAll(pageable);
        List<Property> content = all.getContent();
        List<PropertyDto> collect = content.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        PropSortDto<PropertyDto> propertySortDto = new PropSortDto<>();
        propertySortDto.setDto(collect);
        propertySortDto.setPageNo(all.getTotalPages());
        propertySortDto.setPageSize(all.getSize());
        //propertySortDto.setSortBy(all.getSort());
        return propertySortDto;
    }

    @Override
    public Property getById(long propertyid) {
        Property property = propertyRepository.findById(propertyid).get();
        return property;
    }


    Property mapToEntity(PropertyDto propertyDto)
    {
        Property property = new Property();
        property.setName(propertyDto.getName());
        property.setNumberOfBeds(propertyDto.getNumberOfBeds());
        property.setNumberOfBathrooms(propertyDto.getNumberOfBathrooms());
        property.setNumberOfBedrooms(propertyDto.getNumberOfBedrooms());
        property.setNumberOfGuests(propertyDto.getNumberOfGuests());
        property.setCountry(propertyDto.getCountry());
        property.setCity(propertyDto.getCity());
        return property;
    }

    PropertyDto mapToDto(Property property)
    {
        PropertyDto dto= new PropertyDto();
        dto.setId(property.getId());
        dto.setName(property.getName());
        dto.setNumberOfBeds(property.getNumberOfBeds());
        dto.setNumberOfBathrooms(property.getNumberOfBathrooms());
        dto.setNumberOfBedrooms(property.getNumberOfBedrooms());
        dto.setNumberOfGuests(property.getNumberOfGuests());
        dto.setCountry(property.getCountry());
        dto.setCity(property.getCity());
        return dto;
    }
}
