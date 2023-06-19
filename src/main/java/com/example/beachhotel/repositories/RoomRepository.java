package com.example.beachhotel.repositories;

import com.example.beachhotel.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RoomRepository extends JpaRepository<Room, Integer> {
List<Room> findAllByTypeOfRoom(String typeOfRoom);
List<Room> findByAvailableAndTypeOfRoom(boolean available, String typeOfRoom);
}
