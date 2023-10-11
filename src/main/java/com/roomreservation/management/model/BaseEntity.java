package com.roomreservation.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "First name is required")
    private String name;


    @NotBlank(message = "Last name is required")
    private String lastname;

    @Column(name = "names", length = 25, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "lastnames", length = 100, nullable = false)
    public String getLastname() {
        return lastname;
    }

    //    private Long CreationUserId;
//    private Long LastModifiedUserId;
//    private LocalDateTime CreationDate;
//    private LocalDateTime LastModifiedDate;

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

