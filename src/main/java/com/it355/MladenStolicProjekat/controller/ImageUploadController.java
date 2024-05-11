package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import lombok.AllArgsConstructor;
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
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class ImageUploadController {

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";

    private final AccommodationphotoService accommodationphotoService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("images") MultipartFile[] files, @RequestParam("accommodationId") int accommodationId) {
        if (files.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Nema fajlova"));
        }

        Map<String, String> response = new HashMap<>();
        try {
            for (MultipartFile file : files) {
                String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
                String filename = originalFilename.replace(" ", "_");
                String extension = filename.lastIndexOf(".") > 0 ? filename.substring(filename.lastIndexOf(".")) : "";
                String baseFilename = filename.substring(0, filename.lastIndexOf("."));

                String uniqueFilename = baseFilename + "_" + Instant.now().getEpochSecond() + extension;

                Path targetLocation = Paths.get(UPLOAD_DIR + uniqueFilename);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                accommodationphotoService.savePhoto(accommodationId, uniqueFilename);
                response.put(originalFilename, uniqueFilename);
            }
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Greška pri slanju fajlova"));
        }
    }

    @PostMapping("/single")
    public ResponseEntity<Map<String, String>> uploadSingleImage(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String filename = originalFilename.replace(" ", "_");
            String extension = filename.lastIndexOf(".") > 0 ? filename.substring(filename.lastIndexOf(".")) : "";
            String baseFilename = filename.substring(0, filename.lastIndexOf("."));

            String uniqueFilename = baseFilename + "_" + Instant.now().getEpochSecond() + extension;

            Path targetLocation = Paths.get(UPLOAD_DIR + uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", uniqueFilename);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error occurred: " + e.getMessage()));
        }
    }


}
