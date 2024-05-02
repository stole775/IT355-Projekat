package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    final CityRepository cityRepository;

    @GetMapping()
    public ResponseEntity<List<City>> findAll() {
        List<City> cities = cityRepository.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/nameC/{name}")
    public ResponseEntity<List<City>> findByName(@PathVariable String name) {
        List<City> cities = cityRepository.findByNameContains(name);
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }


    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable int id) {
        Optional<City> city = cityRepository.findById(id);
        return city.map(value -> ResponseEntity.ok((City) value)).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/countryId/{id}")
    public ResponseEntity<List<City>> findByCountryId(@PathVariable int id) {
        List<City> cities = cityRepository.findAllByCountryId(id);
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }

}
