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

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 07/12/2024 - 11:53
 */
@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userDAORepository.findByUsername(loginRequest.getUsername());
        System.out.println(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            // Exclude password in the response
            User sanitizedUser = new User();
            sanitizedUser.setId(user.getId());
            sanitizedUser.setUsername(user.getUsername());
            sanitizedUser.setEmail(user.getEmail());
            System.out.println("good");
            return ResponseEntity.ok(sanitizedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}
