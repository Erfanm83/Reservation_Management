package com.roomreservation.management.repository;

import com.roomreservation.management.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Add custom query methods if needed
    // For example, findRoomByName(String name) to find a room by name
    Optional<Room> findByName(String name);

    Optional<Room> findByLocation(String location);
}