package com.example.beachhotel.controllers;
import com.example.beachhotel.entities.Guests;
import com.example.beachhotel.entities.Room;
import com.example.beachhotel.entities.User;
import com.example.beachhotel.repositories.GuestRepository;
import com.example.beachhotel.repositories.RoomRepository;
import com.example.beachhotel.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Controller
@AllArgsConstructor
public class ReservationController {
    private UserRepository userRepository;
    private GuestRepository guestRepository;
    private RoomRepository roomRepository;

    @PostMapping("/createGuest")
    public String createGuest(@RequestParam("checkInDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInDate,
                              @RequestParam("checkOutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOutDate,
                              @RequestParam("passport") String passport,
                              @RequestParam("typeOfRoomRes") String typeOfRoomRes,
                              Principal principal) {

        List<Room> availableRooms = roomRepository.findByAvailableAndTypeOfRoom(true, typeOfRoomRes);
        if (availableRooms.isEmpty()) {
            return "redirect:/noAvailableRooms";
        }
        if(principal==null){
            return "redirect:/login";
        }
        Room selectedRoom = availableRooms.get(0);
        selectedRoom.setAvailable(false);
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Guests guest = new Guests();
        guest.setPassport(passport);
        guest.setTypeOfRoomRes(typeOfRoomRes);
        guest.setUser(user);
        guest.setCheckIn(checkInDate);
        guest.setCheckOut(checkOutDate);
        guest.setTypeOfRoomRes(selectedRoom.getTypeOfRoom());
        long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate)+1;
        int totalPrice = selectedRoom.getPrice() * (int) numberOfDays;
        guest.setTotalPrice(totalPrice);
        guest.setRoom(selectedRoom);
        guestRepository.save(guest);
        return "redirect:/invoice";
    }

    @GetMapping("/invoice")
    public String showInvoice(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Guests guest = guestRepository.findByUser(user);
        model.addAttribute("guest", guest);
        return "invoice";
    }
}