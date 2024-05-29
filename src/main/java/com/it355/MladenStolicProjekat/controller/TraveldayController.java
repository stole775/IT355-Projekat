package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Travelday;
import com.it355.MladenStolicProjekat.service.TraveldayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/travelday")
@Tag(name = "Travel Day Controller", description = "Upravljanje danima putovanja")
public class TraveldayController {
    final TraveldayService traveldayService;

    @GetMapping()
    @Operation(summary = "Preuzmi sve dane putovanja", description = "Ova metoda vraća listu svih dana putovanja")
    public ResponseEntity<List<Travelday>> getAllTravelday() {
        return ResponseEntity.ok(traveldayService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Preuzmi dan putovanja po ID-u", description = "Ova metoda vraća dan putovanja sa zadatim ID-om")
    public ResponseEntity<Travelday> getTraveldayById(@PathVariable int id) {
        List<Travelday> travelday = traveldayService.findById(id);
        if (!travelday.isEmpty()) {
            return ResponseEntity.ok(travelday.get(0));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
