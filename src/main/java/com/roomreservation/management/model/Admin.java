package com.roomreservation.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
public class Admin extends BaseEntity {

    private Boolean Logged;

    @OneToMany(mappedBy = "admin")
    private List<Room> createdRooms;

    // Getters and setters

    public Boolean getLogged() {
        return Logged;
    }

    public void setLogged(Boolean logged) {
        Logged = logged;
    }

    public String getPassword() {
        BaseEntity adminEntity = new BaseEntity();
        return adminEntity.getpassword();
    }

    public void setPassword(String password) {
        BaseEntity adminEntity = new BaseEntity();
        adminEntity.setPassword(new BCryptPasswordEncoder().encode(password));
    }

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
