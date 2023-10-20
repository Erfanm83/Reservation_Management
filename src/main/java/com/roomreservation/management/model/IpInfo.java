package com.roomreservation.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IpInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private String timeZone;
    private String preferredLanguage;

    // Other fields...

    @OneToOne(mappedBy = "ipInfo") // mappedBy indicates the owning side of the relationship
    private Admin admin;

    @OneToOne(mappedBy = "ipInfo") // mappedBy indicates the owning side of the relationship
    private User user;

    // Constructors, getters, setters...

}
