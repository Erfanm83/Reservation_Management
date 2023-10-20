package com.roomreservation.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@Table(name = "room", schema = "rooms")
public class Room extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room name is required")
    private String roomname;

    @NotBlank(message = "description is needed")
    private String description;

    @NotBlank(message = "please enter a valid location")
    private String location;

    // Add bookable times as a list of time slots
    // bookable times as a list of time slots
    // ...
//    @ManyToOne("admin")
//    private List<Timestamp> BookableTimes;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

}

