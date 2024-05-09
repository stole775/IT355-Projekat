package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.entity.Country;
import com.it355.MladenStolicProjekat.service.CityService;
import com.it355.MladenStolicProjekat.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    final CityService cityService;
    final CountryService countryService;
    final ImageUploadController imageUploadController;

    @GetMapping
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
        return city.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/countryId/{id}")
    public ResponseEntity<List<City>> findByCountryId(@PathVariable int id) {
        List<City> cities = cityService.findAllByCountryId(id);
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable int id) {
        cityService.deleteCityById(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/")
    public ResponseEntity<City> addOrUpdateCity(
            @RequestParam("name") String name,
            @RequestParam("countryId") int countryId,
            @RequestParam("opisGrada") String opisGrada,
            @RequestParam("image") MultipartFile image) {

        Country country = countryService.findById(countryId).orElseThrow(() -> new RuntimeException("Country not found"));
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = storeImage(image);
        }

        City city = new City();
        city.setName(name);
        city.setCountry(country);
        city.setOpisGrada(opisGrada);
        city.setSlikaGradaURL(imageUrl);

        City savedCity = cityService.saveOrUpdateCity(city);
        return ResponseEntity.ok(savedCity);
    }

    private String storeImage(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String cleanedFilename = originalFilename != null ? originalFilename.replace(" ", "_") : null;

            if (cleanedFilename != null) {
                Path targetLocation = rootLocation.resolve(cleanedFilename);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                return cleanedFilename;
            } else {
                throw new RuntimeException("File name is invalid");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @GetMapping("/city/image/{id}")
    public ResponseEntity<List<String>> getCityImageUrls(@PathVariable int id) {
        List<String> imageUrls = cityService.findImageUrlByCityId(id);
        if (imageUrls.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageUrls);
    }
    private final Path rootLocation = Paths.get("./src/main/resources/static/images");

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok().contentLength(resource.contentLength())
                        .body(resource);
            } else {
                System.out.println("Fajl ne moze da se otvori ili procita: " + filename);
                throw new RuntimeException("Ne moze da otvori: " + filename);
            }
        } catch (IOException e) {
            System.out.println("I/O GRESKA: "+e);
            throw new RuntimeException("Greska u citanju fajla: " + filename, e);
        }
    }

}
