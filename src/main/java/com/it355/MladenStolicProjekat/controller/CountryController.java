package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Country;
import com.it355.MladenStolicProjekat.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/country")
public class CountryController {

    final CountryService countryService;

    @GetMapping()
    public ResponseEntity<List<Country>> findAll() {
        List<Country> cities = countryService.findAll();
        return ResponseEntity.ok(cities);
    }



    @GetMapping("/nameC/{name}")
    public ResponseEntity<List<Country>> findByNameContains(@PathVariable String name) {
        List<Country> countries = countryService.findByNameContains(name);
        if (countries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(countries);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(@PathVariable int id) {
        List<Country> country = countryService.findById(id);
        if (country.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((Country) countryService.findById(id));
    }


}
