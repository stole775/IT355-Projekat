package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accomodationphoto")
@Tag(name = "Accommodation Photo Controller", description = "Upravljanje fotografijama smeštaja")
public class AccommodationphotoController {
    final AccommodationphotoService accommodationphotoService;

    @GetMapping("")
    @Operation(summary = "Preuzmi sve fotografije smeštaja", description = "Ova metoda vraća listu svih fotografija smeštaja")
    public ResponseEntity<List<Accommodationphoto>> findAll() {
        return ResponseEntity.ok(accommodationphotoService.findAll());
    }

    @GetMapping("/images/{accommodationphotoId}")
    @Operation(summary = "Preuzmi slike smeštaja po ID-u", description = "Ova metoda vraća listu URL-ova slika za zadati ID smeštaja")
    public ResponseEntity<List<String>> findImagesByAccommodationId(@PathVariable int accommodationphotoId) {
        List<String> imageUrls = accommodationphotoService.findImageUrlByAccommodationId(accommodationphotoId);
        if (imageUrls.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageUrls);
    }

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";
    @DeleteMapping("/{id}")
    @Operation(summary = "Brisanje fotografije smeštaja", description = "Ova metoda briše fotografiju smeštaja sa zadatim ID-om")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Optional<?> accimg = accommodationphotoService.getById(id);
        if (accimg.isPresent()) {
            Accommodationphoto ap = (Accommodationphoto) accimg.get();
            Path target = Paths.get(UPLOAD_DIR + ap.getImageUrl());
            accommodationphotoService.delete(id);
            try {
                Files.deleteIfExists(target);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
            }
            return ResponseEntity.ok(accimg);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Not found"));
    }

}
