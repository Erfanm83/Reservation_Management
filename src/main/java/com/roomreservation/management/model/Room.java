package com.roomreservation.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
//@Builder
@RequiredArgsConstructor
public class Room{
    private Long id;

    @NotBlank(message = "Room name is required")
    private String roomname;

    @NotBlank(message = "description is needed")
    private String description;

    @NotBlank(message = "please enter a valid location")
    private String location;
    private String image; // Base64 encoded image data

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    // bookable times as a list of time slots
    // ...
//    private List<Timestamp> BookableTimes;

}

