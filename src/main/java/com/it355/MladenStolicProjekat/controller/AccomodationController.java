package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.service.AccomodationService;
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
@RequestMapping("/api/accommodation")
public class AccomodationController {

    final AccomodationService accomodationService;

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

}
