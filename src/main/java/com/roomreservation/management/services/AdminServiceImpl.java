package com.roomreservation.management.services;

import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.AdminRepository;
import com.roomreservation.management.repository.RoomRepository;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.security.RoomNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private RoomRepository roomRepository;

    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Admin findAdminById(Long id) {
        Admin foundadmin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("admin not found"));
        return foundadmin;
    }

    @Override
    public User updateUser(User user) {
        User updateduser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("user not found"));

        updateduser.setUsername(user.getName());
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
    public void createRoom(Room room) {
        try {
            // Fetch the IP information
            Room checkedRoom = roomRepository.findByRoomname(room.getRoomname()).orElseThrow(() -> new RuntimeException("room not found"));

            // Create a new user
            Room newRoom = new Room();
            // Set room information...
            newRoom.setId(checkedRoom.getId());
            newRoom.setRoomname(checkedRoom.getRoomname());
            newRoom.setLocation(checkedRoom.getLocation());
            newRoom.setDescription(checkedRoom.getDescription());
            newRoom.setCreationDate(checkedRoom.getCreationDate());

            // Save the room to the database
            roomRepository.save(newRoom);

        } catch (Exception e) {
            throw new RoomNotFoundException("Can Not Find such a Room");
        }

    }
}
