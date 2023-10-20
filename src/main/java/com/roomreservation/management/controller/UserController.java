package com.roomreservation.management.controller;

import com.roomreservation.management.DTO.UserRegistrationRequest;
import com.roomreservation.management.model.IpInfo;
import com.roomreservation.management.model.Room;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.RoomRepository;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.security.LoginDeniedException;
import com.roomreservation.management.security.RoomNotFoundException;
import com.roomreservation.management.services.IpInfoService;
import com.roomreservation.management.services.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ReservationService userService;

    @Autowired
    private IpInfoService ipInfoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_USER')") // Requires ROLE_USER to access
    public ResponseEntity<String> registerUser(@CookieValue(value = "Role_User", defaultValue = "Role_User")
                                                   @Valid @RequestBody UserRegistrationRequest request
            , HttpServletRequest httpRequest) {
        try {
            // Extract user's IP address from the request
            String ipAddress = httpRequest.getHeader("HTTP_X_FORWARDED_FOR");
            if (ipAddress == null) {
                ipAddress = httpRequest.getRemoteAddr();
            }

            // Call the service to get IP information
            IpInfo ipInfo = ipInfoService.getIpInfo(ipAddress);

            // Associate this IpInfo with the user
            // Create a new user
            User user = new User();

            user.setUsername(request.getFirstName());
            user.setLastname(request.getLastName());
            user.setEmail(request.getEmail());
            user.setGender(request.getGender());
            user.setDateOfBirth(request.getDateOfBirth());
            user.setMaritalStatus(request.getMaritalStatus());

            // Link the user with the fetched IP information
            user.setIpInfo(ipInfo);

            // Save the user to the database
            userService.saveNewUser(user);

            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @PostMapping("/login")
    @PreAuthorize("hasRole('ROLE_USER')") // Requires ROLE_USER to access
    public ResponseEntity<String> userLogin(@CookieValue(value = "Role_User", defaultValue = "Role_User")
                                                @RequestParam String username, @RequestParam String password) {
        try {
            Optional<User> user = userRepository.findByUsername(username);

            if (user.isPresent() && passwordEncoder.matches(password, user.get().getpassword())) {
                user.get().setLogged(true);
                return ResponseEntity.ok("User Logged in successful!");
            } else {
                throw new LoginDeniedException("Login Failed!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @PostMapping("/{userId}/upload-photo")
    @PreAuthorize("hasRole('ROLE_USER')") // Requires ROLE_USER to access
    public ResponseEntity<String> uploadProfilePhoto(
            @CookieValue(value = "Rol_Usere", defaultValue = "Role_User")
            @PathVariable Long userId,
            @RequestParam("photoBase64") String photoBase64) {

        try {
            Optional<User> user = userRepository.findById(userId);
            byte[] imageBytes = Base64.getDecoder().decode(photoBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            if (ImageIO.read(bis) == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid image format");
            }

            // Rest of the code...
            if (user != null) {
                user.get().setProfilePhotoBase64(photoBase64);
                userService.saveNewUser(user.get());
                return ResponseEntity.ok("Profile photo uploaded successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }

    @PostMapping("/reserve-room")
    @PreAuthorize("hasRole('ROLE_USER')") // Requires ROLE_USER to access
    public ResponseEntity<String> reserve(@CookieValue(value = "Role_User", defaultValue = "Role_User") @Valid @RequestParam String name, @Valid @RequestBody Room room) {

        try {
            Optional<User> permittedUser = userRepository.findByUsername(name);
            //check if admin is currently logged in
            if (permittedUser.isPresent() && permittedUser.get().getLogged()) {
                //check if that room is available
                if (room != null && roomRepository.findByRoomname(room.getRoomname()).isEmpty()) {
                    userService.createRoom(room);
                    return ResponseEntity.ok("Room created!");
                } else {
                    throw new RoomNotFoundException("Room not found");
                }
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not Found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/room-list")
    @PreAuthorize("hasRole('ROLE_USER')") // Requires ROLE_USER to access
    public ResponseEntity<List<Room>> roomList() {
        try {
            return ResponseEntity.ok(userService.findAllRooms());
        } catch (Exception e) {
            return (ResponseEntity<List<Room>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
