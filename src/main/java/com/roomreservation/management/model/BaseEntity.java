package com.roomreservation.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
public class BaseEntity {

    private UUID Id;

    @Getter
    @Setter
    @NotBlank(message = "First name is required")
    @Column(name = "names", length = 100, nullable = false)
    private String name;

    @Getter
    @Setter
    @NotBlank(message = "Last name is required")
    private String lastname;

//    private Long CreationUserId;
//    private Long LastModifiedUserId;
//    private LocalDateTime CreationDate;
//    private LocalDateTime LastModifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        this.Id = id;
    }

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

