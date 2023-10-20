package com.roomreservation.management.services;

import com.roomreservation.management.model.MeetingRoom;
import com.roomreservation.management.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface MeetingRoomService {

    List<MeetingRoom> findByAll();

    MeetingRoom findById(Long id);
}
