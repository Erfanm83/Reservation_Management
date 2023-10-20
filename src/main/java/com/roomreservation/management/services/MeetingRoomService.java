package com.roomreservation.management.services;

import com.roomreservation.management.model.MeetingRoom;

import java.util.List;

public interface MeetingRoomService {

    List<MeetingRoom> findByAll();

    MeetingRoom findById(Long id);
}
