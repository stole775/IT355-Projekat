package com.it355.MladenStolicProjekat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "accommodationphotos")
public class Accommodationphoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodationId")
    private Accommodation accommodation;

    @Column(name = "imageUrl")
    private String imageUrl;
}
