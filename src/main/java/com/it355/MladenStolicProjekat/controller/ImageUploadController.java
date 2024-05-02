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
import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageUploadController {

    private static final String TARGET_FOLDER = "/src/images/";
    private static final String UPLOAD_DIR = TARGET_FOLDER + "/upload/";

    private final AccommodationphotoService accommodationphotoService;

    public ImageUploadController(AccommodationphotoService accommodationphotoService) {
        this.accommodationphotoService = accommodationphotoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("images") MultipartFile[] files, @RequestParam("accommodationId") int accommodationId) {
        if (files.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No files uploaded");
        }

        try {
            for (MultipartFile file : files) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.write(path, bytes);



                accommodationphotoService.savePhoto(accommodationId, path.toString());
            }

            return ResponseEntity.ok("Files uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }
}
