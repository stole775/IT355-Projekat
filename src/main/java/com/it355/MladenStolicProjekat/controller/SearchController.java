package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.entity.Country;
import com.it355.MladenStolicProjekat.repository.CityRepository;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import com.it355.MladenStolicProjekat.service.AccomodationService;
import com.it355.MladenStolicProjekat.service.CountryService;
import com.it355.MladenStolicProjekat.service.TraveldayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    final TraveldayService traveldayService;
    final CountryService countryService;
    final CityRepository cityRepository;
    final AccomodationService accomodationService;
    final AccommodationphotoService accommodationphotoService;

    @GetMapping("/city/{name}")
    public ResponseEntity<List<City>> searchCityTop4(@PathVariable String name) {
        List<City> cities = cityRepository.findByNameContains(name);
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }
    @GetMapping("/accommodation/{name}")
    public ResponseEntity<List<Accommodation>> searchAccomodationTop4(@PathVariable String name) {
        List<Accommodation> acc = accomodationService.findByNameContainsIgnoreCase(name);
        if (acc.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(acc);
    }
    @GetMapping("/country/{name}")
    public ResponseEntity<List<Country>> searchCountriesTop4(@PathVariable String name) {
        List<Country> countries = countryService.findByNameContainsIgnoreCase(name);
        if (countries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(countries);
    }

}

