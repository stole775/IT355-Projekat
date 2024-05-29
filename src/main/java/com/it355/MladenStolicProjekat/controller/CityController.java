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
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "City Controller", description = "Upravljanje gradovima")
@AllArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    final CityService cityService;
    final CountryService countryService;
    final ImageUploadController imageUploadController;
    final AccommodationphotoService accommodationphotoService;
    final AccomodationService accomodationService;

    @GetMapping
    @Operation(summary = "Preuzmi sve gradove", description = "Ova metoda vraća listu svih gradova")
     public ResponseEntity<List<City>> findAll() {
        List<City> cities = cityService.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/nameC/{name}")
    @Operation(summary = "Pretraga gradova po imenu", description = "Ova metoda vraća listu gradova koji sadrže zadato ime")
     public ResponseEntity<List<City>> findByName(@PathVariable String name) {
        List<City> cities = cityService.findByNameContains(name);
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Preuzmi grad po ID-u", description = "Ova metoda vraća grad sa zadatim ID-om")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<?> city = cityService.findById(id);
        return city.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/countryId/{id}")
    @Operation(summary = "Pretraga gradova po ID-u zemlje", description = "Ova metoda vraća listu gradova koji pripadaju zadatoj zemlji")
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
    @Operation(summary = "Brisanje grada", description = "Ova metoda briše grad sa zadatim ID-om")
    public ResponseEntity<?> deleteCity(@PathVariable int id) {
        Optional<City> cityOpt = cityService.findById(id);
        if (cityOpt.isEmpty()) {
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
    @Operation(summary = "Dodavanje ili ažuriranje grada", description = "Ova metoda dodaje ili ažurira grad sa zadatim parametrima")
    public ResponseEntity<City> addOrUpdateCity(//npravi posle
            @RequestParam("name") String name,
            @RequestParam("countryId") int countryId,
            @RequestParam("opisGrada") String opisGrada,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "id", required = false) Optional<Integer> cityId) {

        Country country = countryService.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found"));

        String imageUrl = null;
        City city;

        if (cityId.isPresent()) {
            city = cityService.findById(cityId.get())
                    .orElseThrow(() -> new RuntimeException("City not found"));

            if (image != null && !image.isEmpty()) {
                if (city.getSlikaGradaURL() != null && !city.getSlikaGradaURL().isEmpty()) {
                    Path oldImagePath = Paths.get(UPLOAD_DIR + city.getSlikaGradaURL());
                    if (Files.exists(oldImagePath)) {
                        try {
                            Files.delete(oldImagePath);
                        } catch (IOException e) {
                            System.err.println("Failed to delete old image: " + e.getMessage());
                        }
                    }
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
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }

            String cleanedFilename = originalFilename.replace(" ", "_");
            String extension = cleanedFilename.lastIndexOf(".") > 0 ? cleanedFilename.substring(cleanedFilename.lastIndexOf(".")) : "";
            String baseFilename = cleanedFilename.substring(0, cleanedFilename.lastIndexOf("."));

            // Generate a unique filename using the current epoch seconds
            String uniqueFilename = baseFilename + "_" + Instant.now().getEpochSecond() + extension;

            Path targetLocation = rootLocation.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @GetMapping("/city/image/{id}")
    @Operation(summary = "Preuzmi slike grada", description = "Ova metoda vraća URL-ove slika grada sa zadatim ID-om")
    public ResponseEntity<List<String>> getCityImageUrls(@PathVariable int id) {
        List<String> imageUrls = cityService.findImageUrlByCityId(id);
        if (imageUrls.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageUrls);
    }



}
