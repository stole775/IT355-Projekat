package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import com.it355.MladenStolicProjekat.service.AccomodationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accommodation")
public class AccomodationController {

    final AccomodationService accomodationService;
    final AccommodationphotoService accommodationphotoService;

    @GetMapping("")
    public ResponseEntity<List<Accommodation>> getAllAccommodations() {
        List<Accommodation> list = accomodationService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();  // Vraća 204 No Content
        }
        return ResponseEntity.ok(list);  // Vraća 200 OK sa listom
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Accommodation>> findByName(  @PathVariable String name) {
        List<Accommodation> list = accomodationService.findByName(name);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();  // Vraća 204 No Content
        }
        return ResponseEntity.ok(list);  // Vraća 200 OK sa listom
    }
    @GetMapping("/nameC/{name}")
    public ResponseEntity<List<Accommodation>> findByNameContaining( @PathVariable String name) {
        List<Accommodation> list = accomodationService.findByNameContaining(name);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();  // Vraća 204 No Content
        }
        return ResponseEntity.ok(list);  // Vraća 200 OK sa listom
    }
    @GetMapping("/istaknutiOglasi")
    public ResponseEntity<List<Accommodation>> findByIstaknutiOglasi() {
        return ResponseEntity.ok(accomodationService.istaknutiOglasi());
    }
    @GetMapping("/cityId/{city_id}")
    public ResponseEntity<List<Accommodation>> findByCityId(@PathVariable int city_id) {
        return ResponseEntity.ok(accomodationService.findByCityId(city_id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Accommodation>> findById(@PathVariable int id) {
        return ResponseEntity.ok(accomodationService.findById(id));
    }
    @PostMapping("/")
    public ResponseEntity<?> addOrUpdateAccommodation(@RequestBody Accommodation accommodation) {
        Accommodation savedAccommodation = accomodationService.saveOrUpdateAccommodation(accommodation);
        if (savedAccommodation != null && savedAccommodation.getId() != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedAccommodation.getId());
            response.put("status", "success");
            response.put("message", "Smestaj je dodat.");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error pri cuvanju smestaja"));
        }
    }

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccommodation(@PathVariable int id) {
        Optional<?> opt = accomodationService.findById(id);

        if (opt.isPresent()) {
            Accommodation smestaj = (Accommodation) opt.get();
            List<Accommodationphoto> list = accommodationphotoService.findAllByAccommodationId(smestaj.getId());
            for (Accommodationphoto slika : list) {
                Path target = Paths.get(UPLOAD_DIR + slika.getImageUrl());
                try {
                    Files.deleteIfExists(target);
                    accommodationphotoService.delete(slika.getId());
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
                }
            }
            accomodationService.deleteAccommodationById(id);
            return ResponseEntity.ok(smestaj);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Not found"));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Accommodation> updateAccommodationImage(@PathVariable int id, @RequestParam("imageUrl") String imageUrl, @RequestParam("type") int type) {
        Optional<Accommodation> existingAccommodation = accomodationService.findById(id);
        if (existingAccommodation.isPresent()) {
            Accommodation updatedAccommodation = existingAccommodation.get();
            if (type == 1) {
                updatedAccommodation.setImageUrl(imageUrl);
            } else if (type == 2) {
                updatedAccommodation.setPriceListImageUrl(imageUrl);
            }
            accomodationService.saveOrUpdateAccommodation(updatedAccommodation);
            return ResponseEntity.ok(updatedAccommodation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
