package com.roomreservation.management.controller;

import com.roomreservation.management.model.User;
import com.roomreservation.management.services.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//used for logging in the console
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class HomeController {

    private ReservationService reservationService;

    @PostMapping("/register")
    public ResponseEntity registeruser(@RequestBody User user){

        User savedUser = reservationService.saveNewUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/User/" + savedUser.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteuserById(@PathVariable("userId") UUID userId){

        reservationService.deleteById(userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> listUsers(){
        return reservationService.userlist();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getBeerById(@PathVariable("userId") UUID beerId){

        log.debug("Get User by Id - in controller");

        return reservationService.getUserById(beerId);
    }
}
