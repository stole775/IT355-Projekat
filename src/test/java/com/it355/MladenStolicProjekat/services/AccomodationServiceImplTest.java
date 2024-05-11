package com.it355.MladenStolicProjekat.services;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.repository.AccommodationRepository;
import com.it355.MladenStolicProjekat.service.impl.AccomodationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccomodationServiceImplTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private AccomodationServiceImpl accommodationService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindAll() {
        when(accommodationRepository.findAll()).thenReturn(Arrays.asList(new Accommodation(), new Accommodation()));
        List<Accommodation> results = accommodationService.findAll();
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testFindByName() {
        when(accommodationRepository.findByName("TestName")).thenReturn(Arrays.asList(new Accommodation()));
        List<Accommodation> results = accommodationService.findByName("TestName");
        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void testFindByNameContaining() {
        when(accommodationRepository.findByNameContaining("Name")).thenReturn(Arrays.asList(new Accommodation(), new Accommodation()));
        List<Accommodation> results = accommodationService.findByNameContaining("Name");
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testIstaknutiOglasi() {
        when(accommodationRepository.istaknutiOglasi()).thenReturn(Arrays.asList(new Accommodation()));
        List<Accommodation> results = accommodationService.istaknutiOglasi();
        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void testFindByCityId() {
        when(accommodationRepository.findByCityId(1)).thenReturn(Arrays.asList(new Accommodation()));
        List<Accommodation> results = accommodationService.findByCityId(1);
        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void testFindById() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1);
        when(accommodationRepository.findById(1)).thenReturn(Optional.of(accommodation));
        Optional<Accommodation> result = accommodationService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testSaveOrUpdateAccommodation() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1);
        when(accommodationRepository.save(accommodation)).thenReturn(accommodation);
        Accommodation savedAccommodation = accommodationService.saveOrUpdateAccommodation(accommodation);
        assertNotNull(savedAccommodation);
        assertEquals(1, savedAccommodation.getId());
    }

    @Test
    void testDeleteAccommodationById() {
        doNothing().when(accommodationRepository).deleteById(1);
        accommodationService.deleteAccommodationById(1);
        verify(accommodationRepository, times(1)).deleteById(1);
    }
}
