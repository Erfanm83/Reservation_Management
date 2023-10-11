package com.roomreservation.management.services;

import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;

    @Autowired
    public ReservationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User updateduser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("user not found"));

        updateduser.setName(user.getName());
        updateduser.setLastname(user.getLastname());
        updateduser.setId(user.getId());
        updateduser.setEmail(user.getEmail());
        updateduser.setGender(user.getGender());
        updateduser.setDateOfBirth(user.getDateOfBirth());
        updateduser.setMaritalStatus(user.getMaritalStatus());
        updateduser.setPermission(user.getPermission());
        return userRepository.save(updateduser);
    }

    @Override
    public User findById(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        return foundUser;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
