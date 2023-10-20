package com.roomreservation.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@Table(name = "room", schema = "rooms")
public class MeetingRoom extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Room name is required")
    private String name;

    public MeetingRoom() {
        this("");
    }

    public MeetingRoom(String name) {
        this(0L, name);
    }

    public MeetingRoom(long id, String name) {
        super(id);
        this.name = name;
    }

    // Add bookable times as a list of time slots
    // bookable times as a list of time slots
    // ...
//    @ManyToOne("admin")
//    private List<Timestamp> BookableTimes;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}

