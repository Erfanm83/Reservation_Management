package com.roomreservation.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "room", schema = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room name is required")
    private String roomname;

    @NotBlank(message = "description is needed")
    private String description;

    @NotBlank(message = "please enter a valid location")
    private String location;
    private String image; // Base64 encoded image data

    // Add bookable times as a list of time slots
    // bookable times as a list of time slots
    // ...
//    private List<Timestamp> BookableTimes;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return roomname;
    }

    public void setName(String name) {
        this.roomname = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

