package com.roomreservation.management.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Builder
@RequiredArgsConstructor
public class Room extends BaseEntity{
    private String description;
    private String location;
    private String image; // Base64 encoded image data

    // bookable times as a list of time slots
    // ...
    private List<Timestamp> BookableTimes;

}

