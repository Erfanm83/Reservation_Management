package com.roomreservation.management.services;

import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;

import java.util.List;

public interface ReservationService {
    User saveNewUser(User User);

    User findUserById(Long id);

    List<User> findAll();

    List<Room> findAllRooms();

    void delete(Long UserId);

    void createRoom(Room room);

}
