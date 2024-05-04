package com.it355.MladenStolicProjekat.repository;

import com.it355.MladenStolicProjekat.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByNameContains(String name);
    @Query(value = "SELECT * FROM Cities c WHERE name LIKE %:name% LIMIT 4", nativeQuery = true)
    @Override
    List<City> findAll();
    List<City> findAllByCountryId(int id);
    @Query(value = " SELECT 'slika_gradaurl' FROM `cities`  WHERE id = :id",nativeQuery = true)
    List<String> findImageUrlByCityId(int id);
 }
