package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.entity.Country;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import com.it355.MladenStolicProjekat.service.AccomodationService;
import com.it355.MladenStolicProjekat.service.CityService;
import com.it355.MladenStolicProjekat.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/country")
@Tag(name = "Country Controller", description = "Upravljanje zemljama")
public class CountryController {

    final CountryService countryService;
    final CityService cityService;
    final AccommodationphotoService accommodationphotoService;
    final AccomodationService accomodationService;

    @GetMapping()
    @Operation(summary = "Preuzmi sve zemlje", description = "Ova metoda vraća listu svih zemalja")
    public ResponseEntity<List<Country>> findAll() {
        List<Country> cities = countryService.findAll();
        return ResponseEntity.ok(cities);
    }



    @GetMapping("/nameC/{name}")
    @Operation(summary = "Pretraga zemalja po imenu", description = "Ova metoda vraća listu zemalja koje sadrže zadato ime")
    public ResponseEntity<List<Country>> findByNameContains(@PathVariable String name) {
        List<Country> countries = countryService.findByNameContains(name);
        if (countries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(countries);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Preuzmi zemlju po ID-u", description = "Ova metoda vraća zemlju sa zadatim ID-om")
    public ResponseEntity<Optional<Country>> findById(@PathVariable int id) {
        Optional<Country> country = countryService.findById(id);
        if (country.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(countryService.findById(id));
    }

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";
    @DeleteMapping("/{id}")
    @Operation(summary = "Brisanje zemlje", description = "Ova metoda briše zemlju sa zadatim ID-om")
    public ResponseEntity<?> deleteCountry(@PathVariable int id) {
        Path target = null;
        Optional<?> country = countryService.findById(id);
        if (country.isPresent()) {
            Country countryObj = (Country) country.get();
            target = Paths.get(UPLOAD_DIR +countryObj.getImageUrl());
            try {
                Files.deleteIfExists(target);
            } catch (IOException e) {
                return ResponseEntity.notFound().build();
            }
        }
        List<City> sviGradovi = cityService.findAllByCountryId(id);
        for (City o : sviGradovi) {
            Optional<?> city = cityService.findById(o.getId());
            if (city.isPresent()) {
                City cityObj = (City) city.get();
                List<Accommodation> accommodationList = accomodationService.findByCityId(cityObj.getId());
                for (Accommodation a : accommodationList) {
                    target = Paths.get(UPLOAD_DIR +a.getImageUrl());
                    try {
                        Files.deleteIfExists(target);
                    } catch (IOException e) {
                        return ResponseEntity.notFound().build();
                    }
                    List<String> accommodationphotos= accommodationphotoService.findImageUrlByAccommodationId(a.getId());
                  for (String photo : accommodationphotos) {
                      target = Paths.get(UPLOAD_DIR +photo);
                      try {
                          Files.deleteIfExists(target);
                      } catch (IOException e) {
                          return ResponseEntity.notFound().build();
                      }
                  }
                }
                target = Paths.get(UPLOAD_DIR +cityObj.getSlikaGradaURL());
                try {
                    Files.deleteIfExists(target);
                } catch (IOException e) {
                    return ResponseEntity.notFound().build();
                }
            }
        }

        countryService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
