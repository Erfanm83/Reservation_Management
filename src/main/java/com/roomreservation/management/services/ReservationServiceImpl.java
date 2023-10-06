package com.roomreservation.management.services;

import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private UserRepository userRepository;

    public ReservationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> userlist() {
        return null;
    }

    @Override
    public User getUserById(UUID id) {
        return null;
    }

    @Override
    public User saveNewUser(User user) {
        // Add logic for saving user to the database
        return userRepository.save(user);
    }

    @Override
    public void updateUserById(UUID UserId, User User) {

    }

    @Override
    public void deleteById(UUID UserId) {

    }

    @Override
    public void patchUserById(UUID UserId, User User) {

    }
}
