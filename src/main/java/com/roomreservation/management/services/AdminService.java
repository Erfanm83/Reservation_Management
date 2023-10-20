package com.roomreservation.management.services;

import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;

public interface AdminService {

    Admin findAdminById(Long id);

    User updateUser(User user);

    void createRoom(Room room);
}
