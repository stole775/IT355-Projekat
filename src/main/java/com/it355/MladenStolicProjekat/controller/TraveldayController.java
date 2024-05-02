package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Travelday;
import com.it355.MladenStolicProjekat.service.TraveldayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/travelday")
public class TraveldayController {
    final TraveldayService traveldayService;

    @GetMapping()//vrati sve
    public ResponseEntity<List<Travelday>> getAllTravelday() {
        return ResponseEntity.ok(traveldayService.findAll());
    }

    @GetMapping("/{id}")//vrati po id
    public ResponseEntity<Travelday> getTraveldayById(@PathVariable int id) {
        List<Travelday> travelday = traveldayService.findById(id);
        if (!travelday.isEmpty()) {
            return ResponseEntity.ok(travelday.get(0));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
