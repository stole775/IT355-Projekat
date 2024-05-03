package com.it355.MladenStolicProjekat.repository;

import com.it355.MladenStolicProjekat.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    List<Country> findAll();
    List<Country> findByNameContains(String name);
    Optional<Country> findById(int id);
    @Query(value = "SELECT * FROM Countries WHERE name LIKE %:name% LIMIT 4", nativeQuery = true)
    List<Country> findByNameContainsIgnoreCase(String name);


}
