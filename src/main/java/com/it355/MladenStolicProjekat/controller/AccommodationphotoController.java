package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accomodationphoto")
public class AccommodationphotoController {
    final AccommodationphotoService accommodationphotoService;
    @GetMapping("")
    public ResponseEntity<List<Accommodationphoto>> getAccommodationphoto() {
        return ResponseEntity.ok(accommodationphotoService.findAll());

    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Accommodationphoto>> getAccommodationphotoById(@PathVariable int id) {
        return ResponseEntity.ok(accommodationphotoService.findAllByid(id));

    }
    @GetMapping("/image/accommodationId/{accommodationId}")
    public ResponseEntity<List<String>> getAccommodationphotoImage(@PathVariable int accommodationId) {
        return ResponseEntity.ok( accommodationphotoService.imageURLbyAccommodationId(accommodationId));
    }

}
