    package com.airbnb.controller;

    import com.airbnb.dto.CountryDto;
    import com.airbnb.service.CountryServiceImpl;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/v1/country")
    public class CountryController {
        //http://localhost:8080/api/v1/country/createcountry
        private CountryServiceImpl countryServiceImpl;

        public CountryController(CountryServiceImpl countryServiceImpl) {
            this.countryServiceImpl = countryServiceImpl;
        }

        @PostMapping("/createcountry")
        public ResponseEntity<CountryDto> createCountry(
                @RequestBody CountryDto countryDto)
        {
            CountryDto dto = countryServiceImpl.createCountry(countryDto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }
    }
