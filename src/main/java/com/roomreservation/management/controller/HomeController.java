package com.roomreservation.management.controller;

import com.roomreservation.management.model.User;
import com.roomreservation.management.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//used for logging in the console
@Slf4j
//@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Validated
public class HomeController {

    private final ReservationService reservationService;
    public HomeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable("userId") Long userId){

        reservationService.delete(userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateById(@Valid @RequestBody User user){

        return ResponseEntity.ok(reservationService.updateUser(user));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user){

        User savedUser = reservationService.saveNewUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/User/" + savedUser.getId().toString());

        return ResponseEntity.status(200).body(new User());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> listUsers(){
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@Valid @PathVariable Long userId){

        log.debug("Get User by Id - in controller");

        return ResponseEntity.ok(reservationService.findById(userId));
    }
}
