package com.roomreservation.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_reservation_meeting_room_id"))
    private MeetingRoom meetingRoom;

    @CreationTimestamp
    @Column(updatable = false , nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    @NotBlank(message = "description is needed")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "please enter a valid location")
    private String location;

    public Reservation() {
        super(0L);
    }

    public Reservation(String username, MeetingRoom meetingRoom, LocalDate date, LocalTime startTime, LocalTime endTime , String description , String location) {
        this(0L, username, meetingRoom, date, startTime, endTime, description, location);
    }

    public Reservation(long id, String username, MeetingRoom meetingRoom, LocalDate date, LocalTime startTime, LocalTime endTime, String description, String location) {
        super(id);
        this.username = username;
        this.meetingRoom = meetingRoom;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.location = location;
        validate();
    }

    public static Reservation of(String username, MeetingRoom meetingRoom, LocalDate date, LocalTime startTime, LocalTime endTime, String description, String location) {
        return new Reservation(username, meetingRoom, date, startTime, endTime, description, location);
    }

    private void validate() {
        if (!isValidMinute(startTime)) throw new IllegalArgumentException("invalid time.minute");
        if (!isValidMinute(endTime)) throw new IllegalArgumentException("invalid time.minute");
        if (!isEndTimeLaterThanStartTime()) throw new IllegalArgumentException("EndTime is NOT later than StartTime");
    }

    private boolean isValidMinute(LocalTime time) {
        int minute = time.getMinute();
        if (minute == 0 || minute == 30) return true;
        return false;
    }

    private boolean isEndTimeLaterThanStartTime() {
        if (endTime.isAfter(startTime)) return true;
        return false;
    }
}
