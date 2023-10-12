package com.roomreservation.management.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.roomreservation.management.model.Admin;
import com.roomreservation.management.model.BaseEntity;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.AdminRepository;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.services.ReservationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//used for logging in the console
@Slf4j
//@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Validated
public class HomeController {

    private final ReservationService reservationService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public HomeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable("userId") Long userId) {

        reservationService.delete(userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateById(@Valid @RequestBody User user) {

        return ResponseEntity.ok(reservationService.updateUser(user));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {

        User savedUser = reservationService.saveNewUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/User/" + savedUser.getId().toString());

        return ResponseEntity.status(200).body(new User());
    }
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestParam String username , @RequestParam String password){
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password , user.get().getpassword())){
            return ResponseEntity.ok("User Logged in successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    @PostMapping("/admin/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<Admin> admin = adminRepository.findByUsername(username);

        if (admin.isPresent() && passwordEncoder.matches(password, admin.get().getPassword())) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@Valid @PathVariable Long userId) {

        log.debug("Get User by Id - in controller");

        return ResponseEntity.ok(reservationService.findUserById(userId));
    }
}
