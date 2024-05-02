package com.it355.MladenStolicProjekat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "traveldays")
public class Travelday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodationId")
    private Accommodation accommodation;

    @Column(name = "dayNumber")
    private Integer dayNumber;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

}