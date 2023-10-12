package com.roomreservation.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "user", schema = "users")
public class User extends BaseEntity {

    @NotBlank(message = "Please enter a valid name")
    private String name;

    @NotBlank(message = "Please enter a valid lastname")
    private String lastname;

    private String avatar; // Base64 encoded image data
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Please enter a valid gender")
    private String gender;
    @Email(message = "Invalid email format")
    private String email;

    private Boolean permission;

    private Boolean logged;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dateofbirth")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "name", length = 20)
    public String getName() {
        return name;
    }

    @Column(name = "lastname", length = 30)
    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public Boolean getLogged() {
        return logged;
    }

    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

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
