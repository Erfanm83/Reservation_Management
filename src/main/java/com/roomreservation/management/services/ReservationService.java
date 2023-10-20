package com.roomreservation.management.services;

import com.roomreservation.management.DTO.ReservationDTO;
import com.roomreservation.management.model.MeetingRoom;
import com.roomreservation.management.model.Reservation;
import com.roomreservation.management.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    User saveNewUser(User User);

    User findUserById(Long id);

    List<User> findAll();

    List<MeetingRoom> findAllRooms();

    void delete(Long UserId);

//    void createRoom(MeetingRoom meetingRoom);

    List<Reservation> get(LocalDate date);

    List<Reservation> add(ReservationDTO reservationDTO);
}
