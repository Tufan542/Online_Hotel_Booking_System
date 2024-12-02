package com.airbnb.controller;

import com.airbnb.dto.PropertyDto;
import com.airbnb.dto.PropSortDto;
import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import com.airbnb.entity.Property;
import com.airbnb.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyService propertyService;
    private CityService cityService;
    private CountryService countryService;

    public PropertyController(PropertyService propertyService, CityService cityService, CountryService countryService) {
        this.propertyService = propertyService;
        this.cityService = cityService;
        this.countryService = countryService;
    }

    //http://localhost:8080/api/v1/property/createproperty?cityId=1&countryId=1
    @PostMapping("/createproperty")
    public ResponseEntity<PropertyDto> addProperty(
            @RequestBody PropertyDto propertyDto,
            @RequestParam long cityId,
            @RequestParam long countryId
    )
    {
        City cityById = cityService.getCityById(cityId);
        Country countryById = countryService.getCountryById(countryId);
        propertyDto.setCity(cityById);
        propertyDto.setCountry(countryById);
        PropertyDto property = propertyService.createProperty(propertyDto);
        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }
    @GetMapping("/propertyresult")
    public List<PropertyDto> searchProperty(
            @RequestParam("city") String name
    )
    {
        List<PropertyDto> propertyDtos = propertyService.searchProperty(name);
        return propertyDtos;
    }
    //http://localhost:8080/api/v1/property?id=2
    @DeleteMapping()
    public ResponseEntity<String> deleteById(@RequestParam long id)
    {
        propertyService.deleteById(id);
        return new ResponseEntity<>("Property Deleted Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/propertyname")
    public ResponseEntity<String> deleteByPropertyName(@RequestParam String name)
    {
        propertyService.deleteByPropertyName(name);
        return new ResponseEntity<>("Property Deleted Successfully", HttpStatus.OK);
    }
    //
    @PutMapping
    public  ResponseEntity<PropertyDto> updateByPropertyId(
            @RequestBody PropertyDto propertyDto ,
            @RequestParam long id
    )
    {
        PropertyDto dto = propertyService.updateById(propertyDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/v1/property/byPropertyId?id=6
    @GetMapping("/byPropertyId")
    public ResponseEntity<PropertyDto> getPropertyById(
            @RequestParam long id
    )
    {
        PropertyDto allPropertyById = propertyService.getAllPropertyById(id);
        return  new ResponseEntity<>(allPropertyById, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/property/byProperty?pageNo=0&pageSize=5&sortBy=name&sortDir=asc
    @GetMapping("/byProperty")
    public ResponseEntity<PropSortDto<PropertyDto>> getProperty(
            @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="pageSize", defaultValue = "5" ,required = false) int pageSize,
            @RequestParam(name="sortBy",defaultValue = "name",required = false)String sortBy,
            @RequestParam(name="sortDir",defaultValue = "name",required = false)String sortDir
    )
    {
        PropSortDto<PropertyDto> property = propertyService.getProperty(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }
}
