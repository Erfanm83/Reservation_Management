package com.roomreservation.management.services;

import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.RoomRepository;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.security.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    @Autowired
    public ReservationServiceImpl(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public User saveNewUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public User findUserById(Long id) {
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

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
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
