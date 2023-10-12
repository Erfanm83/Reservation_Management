package com.roomreservation.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Admin extends BaseEntity {

    @OneToMany(mappedBy = "admin")
    private List<Room> createdRooms;

    // Getters and setters

    public List<Room> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(List<Room> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public void createRoom(Room room) {
        createdRooms.add(room);
    }

    public User GetPermission(User user) {

        user.setPermission(true);
        return user;
    }

    public User RejectPermission(User user) {
        user.setPermission(false);
        return user;
    }

}
