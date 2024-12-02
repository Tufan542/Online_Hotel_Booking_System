package com.airbnb.controller;

import com.airbnb.dto.CityDto;
import com.airbnb.service.CityServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/city")
public class CityController {
    //http://localhost/8080/api/v1/city/createcity
    private CityServiceImpl cityserviceimpl;
    public CityController(CityServiceImpl cityserviceimpl){
        this.cityserviceimpl=cityserviceimpl;
    }

    @PostMapping("/createcity")
    public ResponseEntity<CityDto> addcity(
            @RequestBody CityDto cityDto
    )
    {
        CityDto dto = cityserviceimpl.createCity(cityDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
