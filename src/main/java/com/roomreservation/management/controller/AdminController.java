package com.roomreservation.management.controller;

import com.roomreservation.management.DTO.ReservationDTO;
import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.MeetingRoom;
import com.roomreservation.management.model.Reservation;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.AdminRepository;
import com.roomreservation.management.repository.ReservationRepository;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.security.LoginDeniedException;
import com.roomreservation.management.security.PermissionDeniedException;
import com.roomreservation.management.security.RoomNotFoundException;
import com.roomreservation.management.services.AdminService;
import com.roomreservation.management.services.ReservationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//used for logging in the console
@Slf4j
@RestController
@Validated
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<?> deleteUserById(@CookieValue(value = "Role_Admin", defaultValue = "Role_User")
            @Valid @PathVariable("userId") Long userId) {

        try {
            reservationService.delete(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return (ResponseEntity<User>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<User> updateById(@CookieValue(value = "Role_Admin", defaultValue = "Role_User") @Valid @RequestBody User user) {
        try {
            return ResponseEntity.ok(adminService.updateUser(user));
        } catch (Exception e) {
            return (ResponseEntity<User>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<String> login(@CookieValue(value = "Role_Admin", defaultValue = "Role_User") @RequestParam String adminusername, @RequestParam String adminpassword) {
        try {
            Optional<Admin> admin = adminRepository.findByUsername(adminusername);

            if (admin.isPresent() && passwordEncoder.matches(adminpassword, admin.get().getPassword())) {
                //admin in now logged in
                admin.get().setLogged(true);
                return ResponseEntity.ok("Admin Login successful!");
            } else {
                throw new LoginDeniedException("Login Failed!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @PostMapping("/reserve-room")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_USER to access
    public ResponseEntity<String> reserve(@CookieValue(value = "Role_Admin", defaultValue = "Role_User") @Valid @RequestBody ReservationDTO reservationDTO) {

        try {
            //check if that room is available
            if (reservationDTO != null && !reservationRepository.findByRoomname(reservationDTO.getUsername()).isEmpty()) {
                adminService.add(reservationDTO);
                return ResponseEntity.ok("Room created!");
            } else {
                throw new RoomNotFoundException("Couldn't create Room");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/room-list")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_USER to access
    public ResponseEntity<List<Reservation>> get(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        try {
            return ResponseEntity.ok(adminService.get(date));
        } catch (Exception e) {
            return (ResponseEntity<List<Reservation>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user-permission/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<?> permit(@CookieValue(value = "Role_Admin", defaultValue = "Role_User") @Valid @RequestParam String username, @Valid @PathVariable("userId") Long userId) {

        try {
            Optional<Admin> permittedAdmin = adminRepository.findByUsername(username);

            //check if such an admin is available
            if (permittedAdmin.isPresent() && permittedAdmin.get().getLogged()) {
                Optional<User> permittedUser = userRepository.findById(userId);

                //check if such a user with that userId is available
                if (permittedUser.isPresent() && !permittedUser.get().getPermission()) {
                    permittedUser.get().setPermission(true);
                    return ResponseEntity.ok(reservationService.findAll());
                } else
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User already had permission");
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin not Found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<List<User>> listUsers() {
        try {
            return ResponseEntity.ok(reservationService.findAll());
        } catch (Exception e) {
            return (ResponseEntity<List<User>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<User> getUserById(@CookieValue(value = "Role_Admin", defaultValue = "Role_User") @Valid @PathVariable Long userId) {

        log.debug("Get User by Id - in controller");

        return ResponseEntity.ok(reservationService.findUserById(userId));
    }

}
