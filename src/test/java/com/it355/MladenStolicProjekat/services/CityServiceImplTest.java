package com.it355.MladenStolicProjekat.services;

import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.repository.CityRepository;
import com.it355.MladenStolicProjekat.service.impl.CityServiceImpl;
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
public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void testFindByNameContains() {
        when(cityRepository.findByNameContains("Test")).thenReturn(Arrays.asList(new City(), new City()));
        List<City> results = cityService.findByNameContains("Test");
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testFindAll() {
        when(cityRepository.findAll()).thenReturn(Arrays.asList(new City(), new City()));
        List<City> results = cityService.findAll();
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testFindById() {
        City city = new City();
        city.setId(1);
        when(cityRepository.findById(1)).thenReturn(Optional.of(city));
        Optional<City> result = cityService.findById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void testFindAllByCountryId() {
        when(cityRepository.findAllByCountryId(1)).thenReturn(Arrays.asList(new City(), new City()));
        List<City> results = cityService.findAllByCountryId(1);
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testSaveOrUpdateCity() {
        City city = new City();
        city.setId(1);
        when(cityRepository.save(city)).thenReturn(city);
        City savedCity = cityService.saveOrUpdateCity(city);
        assertNotNull(savedCity);
        assertEquals(1, savedCity.getId());
    }

    @Test
    void testFindImageUrlByCityId() {
        when(cityRepository.findImageUrlByCityId(1)).thenReturn(Arrays.asList("url1", "url2"));
        List<String> urls = cityService.findImageUrlByCityId(1);
        assertNotNull(urls);
        assertEquals(2, urls.size());
        assertTrue(urls.contains("url1") && urls.contains("url2"));
    }

    @Test
    void testDeleteCityById() {
        doNothing().when(cityRepository).deleteById(1);
        cityService.deleteCityById(1);
        verify(cityRepository, times(1)).deleteById(1);
    }
}
