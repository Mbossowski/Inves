package com.example.Inves.controllers;

import com.example.Inves.persistence.repositories.UserDAORepository;
import com.example.Inves.services.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inves.models.User;
import com.example.Inves.requestmodels.UserRequest;
import com.example.Inves.services.*;

import java.util.Objects;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 07/12/2024 - 11:53
 */
@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserDAORepository userDAORepository;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try {

            // Register the user
            User newUser = userService.registerUser(userRequest);

            // Respond with success if user is created
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User loginRequest) {
        User user = userDAORepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        System.out.println(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            // Exclude password in the response
            User sanitizedUser = new User();
            sanitizedUser.setId(user.getId());
            sanitizedUser.setUsername(user.getUsername());
            sanitizedUser.setPassword(user.getPassword());
            sanitizedUser.setFirstName(user.getFirstName());
            sanitizedUser.setLastName(user.getLastName());
            sanitizedUser.setTitle(user.getTitle());
            sanitizedUser.setPhone(user.getPhone());
            sanitizedUser.setCountry(user.getCountry());
            sanitizedUser.setCity(user.getCity());
            sanitizedUser.setStreet(user.getStreet());
            sanitizedUser.setEmail(user.getEmail());
            sanitizedUser.setIban(user.getIban());
            System.out.println("good");
            return ResponseEntity.ok(sanitizedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserData(@RequestParam String username, @RequestParam String password) {
        // Find the user by username
        User user = userDAORepository.findByUsername(username);
        System.out.println(username);
        System.out.println(password);
        System.out.println(user.getPassword());
        if (Objects.equals(password, user.getPassword())) {

            // Create a sanitized response without the password
            User sanitizedUser = new User();
            sanitizedUser.setId(user.getId());
            sanitizedUser.setUsername(user.getUsername());
            sanitizedUser.setFirstName(user.getFirstName());
            sanitizedUser.setLastName(user.getLastName());
            sanitizedUser.setTitle(user.getTitle());
            sanitizedUser.setPhone(user.getPhone());
            sanitizedUser.setEmail(user.getEmail());
            sanitizedUser.setCountry(user.getCountry());
            sanitizedUser.setCity(user.getCity());
            sanitizedUser.setStreet(user.getStreet());
            sanitizedUser.setIban(user.getIban());
            return ResponseEntity.ok(sanitizedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> setUserData(
            @RequestParam String username,
            @RequestParam String password,
            @RequestBody UserRequest updatedData) {
        // Find the user by username
        User user = userDAORepository.findByUsername(username);

        // Validate the user and password
        if (user != null && password == user.getPassword()) {
            // Update the allowed fields
            if (updatedData.getPhone() != null) {
                user.setPhone(updatedData.getPhone());
            }
            if (updatedData.getCountry() != null) {
                user.setCountry(updatedData.getCountry());
            }
            if (updatedData.getCity() != null) {
                user.setCity(updatedData.getCity());
            }
            if (updatedData.getStreet() != null) {
                user.setStreet(updatedData.getStreet());
            }

            if (updatedData.getIban() != null) {
                user.setIban(updatedData.getIban());
            }

            // Save the updated user to the database
            userDAORepository.save(user);

            // Return the updated user data (sanitized)
            User sanitizedUser = new User();
            sanitizedUser.setId(user.getId());
            sanitizedUser.setUsername(user.getUsername());
            sanitizedUser.setFirstName(user.getFirstName());
            sanitizedUser.setLastName(user.getLastName());
            sanitizedUser.setTitle(user.getTitle());
            sanitizedUser.setPhone(user.getPhone());
            sanitizedUser.setCountry(user.getCountry());
            sanitizedUser.setCity(user.getCity());
            sanitizedUser.setStreet(user.getStreet());
            sanitizedUser.setIban(user.getIban());

            return ResponseEntity.ok(sanitizedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }


}
