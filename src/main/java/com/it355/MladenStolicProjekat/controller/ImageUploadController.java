package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class ImageUploadController {

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";


    private final AccommodationphotoService accommodationphotoService;

    public ImageUploadController(AccommodationphotoService accommodationphotoService) {
        this.accommodationphotoService = accommodationphotoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("images") MultipartFile[] files, @RequestParam("accommodationId") int accommodationId) {
        if (files.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Nema fajlova"));
        }

        Map<String, String> response = new HashMap<>();
        try {
            for (MultipartFile file : files) {
                String filename = Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
                Path targetLocation = Paths.get(UPLOAD_DIR + filename);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                accommodationphotoService.savePhoto(accommodationId,  filename);
                response.put(file.getOriginalFilename(),  filename);
            }
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Greska pri slanju fajlova"));
        }
    }
}
