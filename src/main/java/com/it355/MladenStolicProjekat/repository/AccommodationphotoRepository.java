package com.it355.MladenStolicProjekat.repository;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationphotoRepository extends JpaRepository<Accommodationphoto, Integer> {

    @Query(value = "SELECT image_url FROM accommodationphotos WHERE accommodation_id = :accommodationId", nativeQuery = true)
List<String> findImageUrlByAccommodationId(int accommodationId);

@Query(value = "SELECT * FROM accommodationphotos WHERE accommodation_id = :accommodationId", nativeQuery = true)
List<Accommodationphoto> findAllByAccommodationId(int accommodationId);

     @Transactional
    void deleteByImageUrl(@Param("imageUrl") String imageUrl);
}
