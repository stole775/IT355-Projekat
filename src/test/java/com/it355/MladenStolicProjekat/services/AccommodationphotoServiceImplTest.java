package com.it355.MladenStolicProjekat.services;
import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import com.it355.MladenStolicProjekat.repository.AccommodationphotoRepository;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import com.it355.MladenStolicProjekat.service.AccomodationService;
import com.it355.MladenStolicProjekat.service.impl.AccommodationphotoServiceImpl;
import com.it355.MladenStolicProjekat.service.impl.AccomodationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccommodationphotoServiceImplTest {

    @Mock
    private AccommodationphotoRepository accommodationphotoRepository;

    @Mock
    private AccomodationServiceImpl accomodationService;

    @InjectMocks
    private AccommodationphotoServiceImpl accommodationphotoService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindImageUrlByAccommodationId() {
        when(accommodationphotoRepository.findImageUrlByAccommodationId(1)).thenReturn(Arrays.asList("url1", "url2"));
        List<String> urls = accommodationphotoService.findImageUrlByAccommodationId(1);
        assertNotNull(urls);
        assertEquals(2, urls.size());
        assertTrue(urls.contains("url1"));
        assertTrue(urls.contains("url2"));
    }

    @Test
    void testFindAll() {
        when(accommodationphotoRepository.findAll()).thenReturn(Arrays.asList(new Accommodationphoto(), new Accommodationphoto()));
        List<Accommodationphoto> photos = accommodationphotoService.findAll();
        assertNotNull(photos);
        assertEquals(2, photos.size());
    }
    @Captor
    private ArgumentCaptor<Accommodationphoto> photoCaptor;

    @Test
    void testSavePhoto() {
        // Arrange
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1);
        when(accomodationService.findById(1)).thenReturn(Optional.of(accommodation));

        Accommodationphoto photo = new Accommodationphoto();
        photo.setImageUrl("url1");
        photo.setAccommodation(accommodation);

        when(accommodationphotoRepository.save(any(Accommodationphoto.class))).thenReturn(photo);

        // Act
        accommodationphotoService.savePhoto(1, "url1");

        // Assert
        verify(accommodationphotoRepository).save(photoCaptor.capture());
        Accommodationphoto savedPhoto = photoCaptor.getValue();
        assertEquals("url1", savedPhoto.getImageUrl());
        assertEquals(1, savedPhoto.getAccommodation().getId());
    }


    @Test
    void testSavePhotoWithInvalidAccommodationId() {
        when(accomodationService.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            accommodationphotoService.savePhoto(99, "url99");
        });
    }
}
