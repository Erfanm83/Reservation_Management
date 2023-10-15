package com.roomreservation.management.controller;

import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.AdminRepository;
import com.roomreservation.management.repository.RoomRepository;
import com.roomreservation.management.repository.UserRepository;
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
@RequestMapping("/api/v1")
@Validated
public class AdminController {

    private final ReservationService adminService;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminController(ReservationService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable("userId") Long userId) {

        adminService.delete(userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateById(@Valid @RequestBody User user) {

        return ResponseEntity.ok(adminService.updateUser(user));
    }

    @PostMapping("/login")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<String> login(@RequestParam String adminusername, @RequestParam String adminpassword) {
        Optional<Admin> admin = adminRepository.findByUsername(adminusername);

        if (admin.isPresent() && passwordEncoder.matches(adminpassword, admin.get().getPassword())) {
            //admin in now logged in
            admin.get().setLogged(true);
            return ResponseEntity.ok("Admin Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/reserve-room")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<String> reserve(@Valid @RequestParam String adminusername, @Valid @RequestBody Room room) {

        Optional<Admin> permittedAdmin = adminRepository.findByUsername(adminusername);

        //check if admin is currently logged in
        if (permittedAdmin.isPresent() && permittedAdmin.get().getLogged()) {
            //check if that room is available
            if (room != null && roomRepository.findByName(room.getName()).isEmpty()) {
                adminService.createRoom(room);
                return ResponseEntity.ok("Room created!");
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Room");
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin not Found");
    }

    @PostMapping("/user-permission/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires ROLE_ADMIN to access
    public ResponseEntity<?> permit(@Valid @RequestParam String username, @Valid @PathVariable("userId") Long userId) {

        Optional<Admin> permittedAdmin = adminRepository.findByUsername(username);

        //check if such an admin is available
        if (permittedAdmin.isPresent() && permittedAdmin.get().getLogged()) {
            Optional<User> permittedUser = userRepository.findById(userId);

            //check if such a user with that userId is available
            if (permittedUser.isPresent() && !permittedUser.get().getPermission()) {
                permittedUser.get().setPermission(true);
                return ResponseEntity.ok(adminService.findAll());
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User already had permission");
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin not Found");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(adminService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@Valid @PathVariable Long userId) {

        log.debug("Get User by Id - in controller");

        return ResponseEntity.ok(adminService.findUserById(userId));
    }
}
