package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
@Tag(name = "Image Upload Controller", description = "Upravljanje upload-om slika")
public class ImageUploadController {

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";

    private final AccommodationphotoService accommodationphotoService;

    @PostMapping("/upload")
    @Operation(summary = "Upload slika", description = "Ova metoda omogućava upload više slika")
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
    @Operation(summary = "Upload jedne slike", description = "Ova metoda omogućava upload jedne slike")
    public ResponseEntity<Map<String, String>> uploadSingleImage(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;//da ne izbacuje gresku dole null
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

    @DeleteMapping("/delete/{imageUrl}")
    @Operation(summary = "Brisanje slike", description = "Ova metoda briše sliku sa zadatim URL-om")
    public ResponseEntity<Map<String, String>> deleteImage(@PathVariable String imageUrl) {
        Path path = Paths.get(UPLOAD_DIR + imageUrl);
        System.out.println("Attempting to delete image with URL: " + imageUrl);
        if (Files.exists(path)) {
            try {
                Files.deleteIfExists(path);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Uspesno obrisano");
                accommodationphotoService.deleteByImageUrl(imageUrl);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Greska u brisanju fajla");
                return ResponseEntity.internalServerError().body(errorResponse);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "File not found"));
        }
    }

    private final Path rootLocation = Paths.get("./src/main/resources/static/images");
    @GetMapping("/images/{filename:.+}")
    @Operation(summary = "Preuzimanje slike", description = "Ova metoda preuzima sliku sa zadatim imenom fajla")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok().contentLength(resource.contentLength())
                        .body(resource);
            } else {
                System.out.println("Fajl ne moze da se otvori ili procita: " + filename);
                throw new RuntimeException("Ne moze da otvori: " + filename);
            }
        } catch (IOException e) {
            System.out.println("I/O GRESKA: "+e);
            throw new RuntimeException("Greska u citanju fajla: " + filename, e);
        }
    }

}
