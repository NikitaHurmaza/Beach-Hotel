package com.example.beachhotel.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "guest")
public class Guests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "passport", nullable = false, length = 50)
    private String passport;

    @Column(name = "typeOfRoomRes", nullable = false, length = 50)
    private String typeOfRoomRes;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "`check in`", nullable = false)
    private LocalDate checkIn;

    @Column(name = "`check out`", nullable = false)
    private LocalDate checkOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Room getRoom() {
        return room;
    }
}