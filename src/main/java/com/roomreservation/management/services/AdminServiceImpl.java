package com.roomreservation.management.services;

import com.roomreservation.management.DTO.ReservationDTO;
import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.MeetingRoom;
import com.roomreservation.management.model.Reservation;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.AdminRepository;
import com.roomreservation.management.repository.ReservationRepository;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.security.RoomNotFoundException;
import com.roomreservation.management.support.Utility;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private ReservationRepository reservationRepository;

    private final MeetingRoomService meetingRoomService;

    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, ReservationRepository reservationRepository, MeetingRoomService meetingRoomService) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.reservationRepository = reservationRepository;
        this.meetingRoomService = meetingRoomService;
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
//
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

        return save(reservationDTO.getUsername(), meetingRoom, dates, startTime, endTime);
    }


    private List<Reservation> save(String username, MeetingRoom meetingRoom, List<LocalDate> dates, LocalTime startTime, LocalTime endTime) {
        List<Reservation> reservations = new ArrayList<>();
        dates.stream()
                .forEach(date -> reservations.add(
                        reservationRepository.save(Reservation.of(username, meetingRoom, date, startTime, endTime))
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
