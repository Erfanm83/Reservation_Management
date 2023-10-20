package com.roomreservation.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    public BaseEntity(Long id) {
        Id = id;
    }

    //    @NotBlank(message = "First username is required")
//    private String username;

//    @NotBlank(message = "Role Should not be null")
//    private String Role;

    @NotBlank(message = "Last name is required")
    private String password;


//    @Column(name = "names", length = 25, nullable = false)
//    public String getusername() {
//        return username;
//    }
//
    @Column(name = "passwords", length = 100, nullable = false)
    public String getpassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return Id;
    }

    //    private Long CreationUserId;
    //    private Long LastModifiedUserId;
    //    @CreatedBy
//    public Long getCreationUserId() {
//        return CreationUserId;
//    }
//
//    public void setCreationUserId(Long creationUserId) {
//        CreationUserId = creationUserId;
//    }
//
//    public Long getLastModifiedUserId() {
//        return LastModifiedUserId;
//    }
//
//    public void setLastModifiedUserId(Long lastModifiedUserId) {
//        LastModifiedUserId = lastModifiedUserId;
//    }
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    public LocalDateTime getCreationDate() {
//        return CreationDate;
//    }
//
//    public void setCreationDate(LocalDateTime creationDate) {
//        CreationDate = creationDate;
//    }
//
//    @UpdateTimestamp
//    public LocalDateTime getLastModifiedDate() {
//        return LastModifiedDate;
//    }
//
//    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
//        LastModifiedDate = lastModifiedDate;
//    }
}

