package com.roomreservation.management.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class UserRegistrationRequest {

    private String ipAddress;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String avatar; // Base64 encoded image data

    private LocalDate dateOfBirth;

    private String gender;

    @Email(message = "Invalid email format")
    private String email;

    private MaritalStatus maritalStatus;

    private String profilePhotoBase64;

    // Add other fields like time zone and preferred language

    // Getters and setters for the fields

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getProfilePhotoBase64() {
        return profilePhotoBase64;
    }

    public void setProfilePhotoBase64(String profilePhotoBase64) {
        this.profilePhotoBase64 = profilePhotoBase64;
    }
}
