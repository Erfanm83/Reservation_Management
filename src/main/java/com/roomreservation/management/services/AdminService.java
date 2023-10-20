package com.roomreservation.management.services;

import com.roomreservation.management.DTO.ReservationDTO;
import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.MeetingRoom;
import com.roomreservation.management.model.Reservation;
import com.roomreservation.management.model.User;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {

    Admin findAdminById(Long id);

    User updateUser(User user);

//    void createRoom(MeetingRoom meetingRoom);

    List<Reservation> get(LocalDate date);

    List<Reservation> add(ReservationDTO reservationDTO);
}
