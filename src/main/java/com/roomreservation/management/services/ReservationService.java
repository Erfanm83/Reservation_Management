package com.roomreservation.management.services;

import com.roomreservation.management.model.User;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    List<User> userlist();

    User getUserById(UUID id);

    User saveNewUser(User User);

    void updateUserById(UUID UserId, User User);

    void deleteById(UUID UserId);

    void patchUserById(UUID UserId, User User);

}
