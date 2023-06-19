package com.example.beachhotel.controllers;

import com.example.beachhotel.entities.Guests;
import com.example.beachhotel.entities.Room;
import com.example.beachhotel.entities.User;
import com.example.beachhotel.repositories.GuestRepository;
import com.example.beachhotel.repositories.RoomRepository;
import com.example.beachhotel.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
@Controller
@AllArgsConstructor
public class MainController {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String showAllRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "mainPage";
    }
    @GetMapping("/Double-Room")
    public String showDoubleRoom(Model model) {
        List<Room> rooms = roomRepository.findAllByTypeOfRoom("Double room");
        model.addAttribute("rooms", rooms);
        return "Double-room";
    }
    @GetMapping("/Triple-Room")
    public String showTripleRoom(Model model) {
        List<Room> rooms = roomRepository.findAllByTypeOfRoom("Triple room");
        model.addAttribute("rooms", rooms);
        return "Triple-room";
    }
    @GetMapping("/Deluxe-Room")
    public String showDeluxeRoom(Model model) {
        List<Room> rooms = roomRepository.findAllByTypeOfRoom("Deluxe room");
        model.addAttribute("rooms", rooms);
        return "Deluxe-room";
    }
    @GetMapping("/room/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Room room= roomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        byte[] image = room.getImage();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    @GetMapping("/Reservation")
    public String showAllRoomsReservation(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "Reservation";
    }

}
