package com.example.beachhotel.controllers;
import com.example.beachhotel.entities.Guests;
import com.example.beachhotel.entities.Room;
import com.example.beachhotel.entities.User;
import com.example.beachhotel.repositories.GuestRepository;
import com.example.beachhotel.repositories.RoomRepository;
import com.example.beachhotel.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AdminController {
    private UserRepository userRepository;
    private GuestRepository guestRepository;
    private RoomRepository roomRepository;

    @GetMapping("admin_page")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getGuestsDepartingToday(Model model) {
        LocalDate today = LocalDate.now();
        List<Guests> departingGuests = guestRepository.findByCheckOut(today);
        List<Guests> guests = guestRepository.findAll();
        List<Guests> longStay = guestRepository.findLongStayGuests();
        model.addAttribute("guests", guests);
        model.addAttribute("departingGuests", departingGuests);
        model.addAttribute("longStay", longStay);
        return "admin-page";
    }

    @PostMapping("/deleteGuest")
    public String releaseRoom(@RequestParam("guestId") Long guestId) {
        Optional<Guests> optionalGuest = guestRepository.findById(guestId);
        if (optionalGuest.isPresent()) {
            Guests guest = optionalGuest.get();
            Room room = guest.getRoom();
            room.setAvailable(true);
            guestRepository.delete(guest);
            return "redirect:/admin_page";
        }
        return "redirect:/admin_page";
    }
    @PostMapping("/extendStay")
    public String extendStay(@RequestParam("guestId") Long guestId,
                             @RequestParam("extensionDays") int extensionDays) {
        Optional<Guests> optionalGuest = guestRepository.findById(guestId);
        if (optionalGuest.isPresent()) {
            Guests guest = optionalGuest.get();
            LocalDate currentCheckOut = guest.getCheckOut();
            LocalDate newCheckOut = currentCheckOut.plusDays(extensionDays);
            guest.setCheckOut(newCheckOut);
            long numberOfDays = ChronoUnit.DAYS.between(guest.getCheckIn(), newCheckOut) + 1;
            int totalPrice = guest.getRoom().getPrice() * (int) numberOfDays;
            guest.setTotalPrice(totalPrice);
            guestRepository.save(guest);
            return "redirect:/admin_page";
        }
        return "redirect:/error";
    }
    @PostMapping("/earlyDeparture")
    public String earlyStay(@RequestParam("guestId") Long guestId,
                             @RequestParam("extensionDays") int extensionDays) {
        Optional<Guests> optionalGuest = guestRepository.findById(guestId);
        if (optionalGuest.isPresent()) {
            Guests guest = optionalGuest.get();
            LocalDate currentCheckOut = guest.getCheckOut();
            LocalDate newCheckOut = currentCheckOut.minusDays(extensionDays);
            guest.setCheckOut(newCheckOut);
            long numberOfDays = ChronoUnit.DAYS.between(guest.getCheckIn(), newCheckOut) + 1;
            int totalPrice = guest.getRoom().getPrice() * (int) numberOfDays;
            guest.setTotalPrice(totalPrice);
            guestRepository.save(guest);
            return "redirect:/admin_page";
        }
        return "redirect:/error";
    }
}