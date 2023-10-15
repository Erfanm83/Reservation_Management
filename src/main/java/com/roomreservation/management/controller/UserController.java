package com.roomreservation.management.controller;

import com.roomreservation.management.DTO.UserRegistrationRequest;
import com.roomreservation.management.model.IpInfo;
import com.roomreservation.management.model.User;
import com.roomreservation.management.repository.UserRepository;
import com.roomreservation.management.services.IpInfoService;
import com.roomreservation.management.services.ReservationService;
import jakarta.validation.Valid;
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
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@Validated
public class UserController {
    private final ReservationService userService;

    private final IpInfoService ipInfoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(ReservationService userService, IpInfoService ipInfoService) {
        this.userService = userService;
        this.ipInfoService = ipInfoService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_USER')") // Requires ROLE_USER to access
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        // Fetch the IP information
        IpInfo ipInfo = ipInfoService.fetchIpInfo(request.getIpAddress());

        // Create a new user
        User user = new User();
        // Set user information...
        user.setName(request.getFirstName());
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
    }

    @PostMapping("/login")
    @PreAuthorize("hasRole('ROLE_USER')") // Requires ROLE_USER to access
    public ResponseEntity<String> userLogin(@RequestParam String username, @RequestParam String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getpassword())) {
            user.get().setLogged(true);
            return ResponseEntity.ok("User Logged in successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/{userId}/upload-photo")
    public ResponseEntity<String> uploadProfilePhoto(
            @PathVariable Long userId,
            @RequestParam("photoBase64") String photoBase64) {

        User user = userService.findUserById(userId);

        try {
            byte[] imageBytes = Base64.getDecoder().decode(photoBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            if (ImageIO.read(bis) == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid image format");
            }

            // Rest of the code...
            if (user != null) {
                user.setProfilePhotoBase64(photoBase64);
                userService.saveNewUser(user);
                return ResponseEntity.ok("Profile photo uploaded successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }

}
