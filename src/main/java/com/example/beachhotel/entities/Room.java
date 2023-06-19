package com.example.beachhotel.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "TypeOfRoom", nullable = false, length = 50)
    private String typeOfRoom;

    @Column(name = "number", nullable = false)
    private Integer number;
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "image")
    private byte[] image;
    @Column(name = "available", nullable = false)
    private boolean available;
}