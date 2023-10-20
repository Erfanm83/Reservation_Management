package com.roomreservation.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
public class Admin extends BaseEntity {

    private Boolean Logged;

    private String username;
    @OneToMany(mappedBy = "admin")
    private List<MeetingRoom> createdMeetingRooms;

    @OneToOne
    @JoinColumn(name = "ip_info_id") // This is the foreign key column name
    private IpInfo ipInfo;

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

    public List<MeetingRoom> getCreatedRooms() {
        return createdMeetingRooms;
    }

    public void setCreatedRooms(List<MeetingRoom> createdMeetingRooms) {
        this.createdMeetingRooms = createdMeetingRooms;
    }

    public void createRoom(MeetingRoom meetingRoom) {
        createdMeetingRooms.add(meetingRoom);
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
