package com.roomreservation.management.services;

import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;

import java.util.List;

public interface ReservationService {
    User saveNewUser(User User);

    User updateUser(User User);

    User findUserById(Long id);

    Admin findAdminById(Long id);

    List<User> findAll();

    void delete(Long UserId);

    void createRoom(Admin admin, Room room);

    // Add other admin-related methods
}
