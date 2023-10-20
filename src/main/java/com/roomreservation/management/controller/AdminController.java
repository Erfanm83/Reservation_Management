package com.roomreservation.management.controller;

import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.AdminRepository;
import com.roomreservation.management.repository.RoomRepository;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.security.LoginDeniedException;
import com.roomreservation.management.security.PermissionDeniedException;
import com.roomreservation.management.services.AdminService;
import com.roomreservation.management.services.ReservationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//used for logging in the console
@Slf4j
@RestController
@Validated
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
    private RoomRepository roomRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    //Constructor
//    public AdminController(ReservationService adminService) {
//        this.adminService = adminService;
//    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable("userId") Long userId) {

        try {
            reservationService.delete(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return (ResponseEntity<User>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateById(@Valid @RequestBody User user) {
        try {
            return ResponseEntity.ok(adminService.updateUser(user));
        } catch (Exception e) {
            return (ResponseEntity<User>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
//    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<String> login(@RequestParam String adminusername, @RequestParam String adminpassword) {
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
//    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<String> reserve(@Valid @RequestParam String adminusername, @Valid @RequestBody Room room) {

        try {
            Optional<Admin> permittedAdmin = adminRepository.findByUsername(adminusername);

            //check if admin is currently logged in
            if (permittedAdmin.isPresent() && permittedAdmin.get().getLogged()) {
                //check if that room is available
                if (room != null && roomRepository.findByName(room.getRoomname()).isEmpty()) {
                    adminService.createRoom(room);
                    return ResponseEntity.ok("Room created!");
                } else
                    throw new PermissionDeniedException("Permission Failed!");
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin not Found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @PostMapping("/user-permission/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<?> permit(@Valid @RequestParam String username, @Valid @PathVariable("userId") Long userId) {

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
    public ResponseEntity<List<User>> listUsers() {
        try {
            return ResponseEntity.ok(reservationService.findAll());
        } catch (Exception e) {
            return (ResponseEntity<List<User>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@Valid @PathVariable Long userId) {

        log.debug("Get User by Id - in controller");

        return ResponseEntity.ok(reservationService.findUserById(userId));
    }

    @GetMapping("/admin")
    public String handleRequest() {
        // Logic specific to handling admin requests
        return "Admin request handled successfully!";
    }
}
