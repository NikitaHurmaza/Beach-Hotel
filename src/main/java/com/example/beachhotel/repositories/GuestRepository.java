package com.example.beachhotel.repositories;

import com.example.beachhotel.entities.Guests;
import com.example.beachhotel.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guests, Integer> {
    Guests findByUser(User user);
    List<Guests> findByCheckOut(LocalDate today);

    Optional<Guests> findById(Long guestId);
    @Query("SELECT g FROM Guests g WHERE DATEDIFF(g.checkOut, g.checkIn) >= 10")
    List<Guests> findLongStayGuests();

}
