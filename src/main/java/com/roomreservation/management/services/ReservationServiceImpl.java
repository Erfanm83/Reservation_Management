package com.roomreservation.management.services;

import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.AdminRepository;
import com.roomreservation.management.repository.RoomRepository;
import com.roomreservation.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final RoomRepository roomRepository;

    @Autowired
    public ReservationServiceImpl(UserRepository userRepository , AdminRepository adminRepository , RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.roomRepository = roomRepository;
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
    public User findUserById(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        return foundUser;
    }

    @Override
    public Admin findAdminById(Long id) {
        Admin foundadmin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("admin not found"));
        return foundadmin;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void createRoom(Admin admin, Room room) {
        // Check if the admin has the necessary privileges to create a room, e.g., based on roles or permissions.
        // Implement your authorization logic here.

        // Save the room to the database
        roomRepository.save(room);
    }
}
