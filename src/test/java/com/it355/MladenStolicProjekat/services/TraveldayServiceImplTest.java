package com.it355.MladenStolicProjekat.services;

import com.it355.MladenStolicProjekat.entity.Travelday;
import com.it355.MladenStolicProjekat.repository.TraveldayRepository;
import com.it355.MladenStolicProjekat.service.impl.TraveldayServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TraveldayServiceImplTest {

    @Mock
    private TraveldayRepository traveldayRepository;

    @InjectMocks
    private TraveldayServiceImpl traveldayService;

    @Test
    void testFindAll() {
        when(traveldayRepository.findAll()).thenReturn(Arrays.asList(new Travelday(), new Travelday()));
        List<Travelday> results = traveldayService.findAll();
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testFindById() {
        int traveldayId = 1;
        when(traveldayRepository.findById(traveldayId)).thenReturn(Arrays.asList(new Travelday()));
        List<Travelday> results = traveldayService.findById(traveldayId);
        assertNotNull(results);
        assertEquals(1, results.size());  // Assuming there's only one Travelday with the given ID for simplicity
    }
}
