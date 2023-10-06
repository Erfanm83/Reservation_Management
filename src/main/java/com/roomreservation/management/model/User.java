package com.roomreservation.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user", schema = "users")
public class User extends BaseEntity {

    private String avatar; // Base64 encoded image data
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private Boolean permission;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;


    // other fields like work history, skills, interests


//    @OneToMany(mappedBy = "user")
//    private List<WorkHistory> workHistories;
//
//    public List<WorkHistory> getWorkHistories() {
//        return workHistories;
//    }
//
//    public void setWorkHistories(List<WorkHistory> workHistories) {
//        this.workHistories = workHistories;
//    }

}
