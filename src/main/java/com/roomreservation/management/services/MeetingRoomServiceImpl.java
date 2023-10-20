package com.roomreservation.management.services;

import com.roomreservation.management.model.MeetingRoom;
import com.roomreservation.management.repository.MeetingRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Override
    public List<MeetingRoom> findByAll() {
        return meetingRoomRepository.findAll();
    }

    @Override
    public MeetingRoom findById(Long id) {
        return meetingRoomRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
