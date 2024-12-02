package com.airbnb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type_of_room", nullable = false)
    private String typeOfRoom;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "count", nullable = false) // Removed unique constraint
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false) // Added nullable = false
    private Property property;

    @Column(name = "date", nullable = false) // Renamed to a more descriptive name
    private LocalDate date;
}
