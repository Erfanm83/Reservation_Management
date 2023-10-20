package com.roomreservation.management.services;

import com.roomreservation.management.DTO.ReservationDTO;
import com.roomreservation.management.model.MeetingRoom;
import com.roomreservation.management.model.Reservation;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.MeetingRoomRepository;
import com.roomreservation.management.repository.ReservationRepository;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.security.RoomNotFoundException;
import com.roomreservation.management.support.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;

    private final ReservationRepository reservationRepository;

    private final MeetingRoomRepository meetingRoomRepository;

    private final MeetingRoomService meetingRoomService;

    @Autowired
    public ReservationServiceImpl(UserRepository userRepository, ReservationRepository reservationRepository, MeetingRoomRepository meetingRoomRepository, MeetingRoomService meetingRoomService) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.meetingRoomRepository = meetingRoomRepository;
        this.meetingRoomService = meetingRoomService;
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
    public List<MeetingRoom> findAllRooms() {
        return meetingRoomRepository.findAll();
    }

//    @Override
//    public void createRoom(MeetingRoom meetingRoom) {
//        try {
//            // Fetch the IP information
//            MeetingRoom checkedMeetingRoom = reservationRepository.findByRoomname(meetingRoom.getRoomname()).orElseThrow(() -> new RuntimeException("room not found"));
//
//            // Create a new user
//            MeetingRoom newMeetingRoom = new MeetingRoom();
//            // Set room information...
//            newMeetingRoom.setId(checkedMeetingRoom.getId());
//            newMeetingRoom.setRoomname(checkedMeetingRoom.getRoomname());
//            newMeetingRoom.setLocation(checkedMeetingRoom.getLocation());
//            newMeetingRoom.setDescription(checkedMeetingRoom.getDescription());
//            newMeetingRoom.setCreationDate(checkedMeetingRoom.getCreationDate());
//
//            // Save the room to the database
//            reservationRepository.save(newMeetingRoom);
//
//        } catch (Exception e) {
//            throw new RoomNotFoundException("Can Not Find such a Room");
//        }
//    }

    @Override
    public List<Reservation> get(LocalDate date) {
        return reservationRepository.findAllByDateOrderByMeetingRoomIdAscStartTimeAsc(date);
    }

    @Override
    public List<Reservation> add(ReservationDTO reservationDTO) {
        MeetingRoom meetingRoom = meetingRoomService.findById(reservationDTO.getMeetingRoomId());
        List<LocalDate> dates = Utility.GetDates(reservationDTO.getDate(), reservationDTO.getRepeatPerWeek());
        LocalTime startTime = reservationDTO.getStartTime();
        LocalTime endTime = reservationDTO.getEndTime();

        if (isOverlapped(meetingRoom.getId(), dates, startTime, endTime))
            throw new IllegalArgumentException("overlapped reservation");

        return save(reservationDTO.getUsername(), meetingRoom, dates, startTime, endTime, reservationDTO.getDescription(), reservationDTO.getLocation());
    }


    private List<Reservation> save(String username, MeetingRoom meetingRoom, List<LocalDate> dates, LocalTime startTime, LocalTime endTime, String description , String location) {
        List<Reservation> reservations = new ArrayList<>();
        dates.stream()
                .forEach(date -> reservations.add(
                        reservationRepository.save(Reservation.of(username, meetingRoom, date, startTime, endTime, description, location))
                ));
        return reservations;
    }

    private boolean isOverlapped(long meetingRoomId, List<LocalDate> dates, LocalTime startTime, LocalTime endTime) {
        Long count = reservationRepository.countOverlapped(meetingRoomId, dates, startTime, endTime);
        if (count <= 0)
            return false;
        return true;
    }
}
