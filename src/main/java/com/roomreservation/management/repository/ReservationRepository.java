package com.roomreservation.management.repository;

import com.roomreservation.management.model.MeetingRoom;
import com.roomreservation.management.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Add custom query methods if needed
    // For example, findRoomByName(String name) to find a room by name
    Optional<Reservation> findByUsername(String name);

    Optional<Reservation> findByLocation(String location);

    List<Reservation> findAllByDateOrderByMeetingRoomIdAscStartTimeAsc(LocalDate date);

    @Query(value = "SELECT COUNT(*) "
            + "FROM reservation AS r "
            + "WHERE r.meeting_room_id = :meetingRoomId "
            + "AND r.date IN :dates "
            + "AND r.start_time < :endTime "
            + "AND r.end_time > :startTime"
            , nativeQuery = true)
    long countOverlapped(
            @Param("meetingRoomId") long meetingRoomId,
            @Param("dates") Collection<LocalDate> dates,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}