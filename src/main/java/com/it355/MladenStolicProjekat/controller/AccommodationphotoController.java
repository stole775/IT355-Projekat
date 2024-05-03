package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accomodationphoto")
public class AccommodationphotoController {
    final AccommodationphotoService accommodationphotoService;
    @GetMapping("")
    public ResponseEntity<List<Accommodationphoto>> findAll() {
        return ResponseEntity.ok(accommodationphotoService.findAll());}


    @GetMapping("/images/{accommodationphotoId}")
    public ResponseEntity<List<String>> findImagesByAccommodationId(@PathVariable int accommodationphotoId) {
        List<String> imageUrls = accommodationphotoService.findImageUrlByAccommodationId(accommodationphotoId);
        if (imageUrls.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageUrls);
    }

}
