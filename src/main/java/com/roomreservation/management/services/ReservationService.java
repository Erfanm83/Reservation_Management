package com.roomreservation.management.services;

import com.roomreservation.management.model.User;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    User saveNewUser(User User);
    User updateUser(User User);
    User findById(UUID id);
    List<User> findAll();
    void deleteById(UUID UserId);


}
