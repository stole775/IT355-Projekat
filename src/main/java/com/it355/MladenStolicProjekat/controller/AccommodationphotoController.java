package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
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
public class AccommodationphotoController {
    final AccommodationphotoService accommodationphotoService;

    @GetMapping("")
    public ResponseEntity<List<Accommodationphoto>> findAll() {
        return ResponseEntity.ok(accommodationphotoService.findAll());
    }

    @GetMapping("/images/{accommodationphotoId}")
    public ResponseEntity<List<String>> findImagesByAccommodationId(@PathVariable int accommodationphotoId) {
        List<String> imageUrls = accommodationphotoService.findImageUrlByAccommodationId(accommodationphotoId);
        if (imageUrls.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageUrls);
    }

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";
    @DeleteMapping("/{id}")
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
