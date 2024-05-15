package com.it355.MladenStolicProjekat.repository;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationphotoRepository extends JpaRepository<Accommodationphoto, Integer> {
    @Query(value = " SELECT image_url FROM AccommodationPhotos  WHERE accommodation_id = :accommodationId",nativeQuery = true)
    List<String> findImageUrlByAccommodationId(int accommodationId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO AccommodationPhoto (accommodationId, imageUrl) VALUES (:accommodationId, :imageUrl)",nativeQuery = true)
    void savePhoto(int accommodationId, String imageUrl);

    @Query(value = " SELECT * FROM AccommodationPhotos  WHERE accommodation_id = :accommodationId",nativeQuery = true)
    List<Accommodationphoto> findAllByAccommodationId(int accommodationId);

}
