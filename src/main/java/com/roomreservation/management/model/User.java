package com.roomreservation.management.model;

import com.roomreservation.management.DTO.MaritalStatus;
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

    @Column(name = "name")
    @NotBlank(message = "Please enter a valid name")
    private String username;

    @Column(name = "lastname")
    @NotBlank(message = "Please enter a valid lastname")
    private String lastname;

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

    @Lob
    private String profilePhotoBase64;

    @NotBlank(message = "Last name is required")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ip_info_id", referencedColumnName = "id")
    private IpInfo ipInfo;
    public User() {
        super(0L);
    }

    public void setProfilePhotoBase64(String profilePhotoBase64) {
        this.profilePhotoBase64 = profilePhotoBase64;
    }

    //Getters and Setters ...
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
        return username;
    }

    @Column(name = "lastname", length = 30)
    public String getLastname() {
        return lastname;
    }

    @Column(name = "password", length = 100, nullable = false)
    public String getpassword() {
        return password;
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
