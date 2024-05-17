package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.entity.Country;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import com.it355.MladenStolicProjekat.service.AccomodationService;
import com.it355.MladenStolicProjekat.service.CityService;
import com.it355.MladenStolicProjekat.service.CountryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    final AccommodationphotoService accommodationphotoService;
    final AccomodationService accomodationService;

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
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<?> city = cityService.findById(id);
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

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable int id) {
        Optional<City> cityOpt = cityService.findById(id);
        if (!cityOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        City city = cityOpt.get();
        List<Accommodation> accommodations = accomodationService.findByCityId(city.getId());

        try {
            for (Accommodation acc : accommodations) {
                List<String> photos = accommodationphotoService.findImageUrlByAccommodationId(acc.getId());
                for (String photo : photos) {
                    System.out.println(photo);
                    Files.deleteIfExists(Paths.get(UPLOAD_DIR + photo));
                }
                Files.deleteIfExists(Paths.get(UPLOAD_DIR + acc.getImageUrl()));
                accomodationService.deleteAccommodationById(acc.getId());
            }

            Files.deleteIfExists(Paths.get(UPLOAD_DIR + city.getSlikaGradaURL()));

            cityService.deleteCityById(city.getId());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete files: " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<City> addOrUpdateCity(//npravi posle
            @RequestParam("name") String name,
            @RequestParam("countryId") int countryId,
            @RequestParam("opisGrada") String opisGrada,
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "id", required = false) Optional<Integer> cityId) {

        Country country = countryService.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found"));

        String imageUrl = null;
        City city;

        if (cityId.isPresent()) {
            city = cityService.findById(cityId.get())
                    .orElseThrow(() -> new RuntimeException("City not found"));

            if (image != null && !image.isEmpty() && city.getSlikaGradaURL() != null) {
                try {
                    Files.deleteIfExists(Paths.get(UPLOAD_DIR + city.getSlikaGradaURL()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                imageUrl = storeImage(image);
            } else {
                imageUrl = city.getSlikaGradaURL();
            }
        } else {
            if (image != null && !image.isEmpty()) {
                imageUrl = storeImage(image);
            }
            city = new City();
        }

        city.setName(name);
        city.setCountry(country);
        city.setOpisGrada(opisGrada);
        city.setSlikaGradaURL(imageUrl);

        City savedCity = cityService.saveOrUpdateCity(city);
        return ResponseEntity.ok(savedCity);
    }

    private final Path rootLocation = Paths.get("./src/main/resources/static/images");
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



}
