package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.entity.Country;
import com.it355.MladenStolicProjekat.service.CityService;
import com.it355.MladenStolicProjekat.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    final CityService cityService;
    final CountryService countryService;
    final ImageUploadController imageUploadController;

    @GetMapping()
    public ResponseEntity<List<City>> findAll() {
        List<City> cities = cityService.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/nameC/{name}")
    public ResponseEntity<List<City>> findByName(@PathVariable String name) {
        List<City> cities = cityService.findByNameContains(name);
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }


    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable int id) {
        Optional<City> city = cityService.findById(id);
        return city.map(value -> ResponseEntity.ok((City) value)).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/countryId/{id}")
    public ResponseEntity<List<City>> findByCountryId(@PathVariable int id) {
        List<City> cities = cityService.findAllByCountryId(id);
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }


}
