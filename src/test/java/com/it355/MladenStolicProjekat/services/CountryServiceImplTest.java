package com.it355.MladenStolicProjekat.services;

import com.it355.MladenStolicProjekat.entity.Country;
import com.it355.MladenStolicProjekat.repository.CountryRepository;
import com.it355.MladenStolicProjekat.service.impl.CountryServiceImpl;
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
public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    @Test
    void testFindAll() {
        when(countryRepository.findAll()).thenReturn(Arrays.asList(new Country(), new Country()));
        List<Country> results = countryService.findAll();
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testFindByNameContains() {
        when(countryRepository.findByNameContains("Test")).thenReturn(Arrays.asList(new Country(), new Country()));
        List<Country> results = countryService.findByNameContains("Test");
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testFindById() {
        Country country = new Country();
        country.setId(1);
        when(countryRepository.findById(1)).thenReturn(Optional.of(country));
        Optional<Country> result = countryService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testFindByNameContainsIgnoreCase() {
        when(countryRepository.findByNameContainsIgnoreCase("test")).thenReturn(Arrays.asList(new Country()));
        List<Country> results = countryService.findByNameContainsIgnoreCase("test");
        assertNotNull(results);
        assertEquals(1, results.size());
    }
}
