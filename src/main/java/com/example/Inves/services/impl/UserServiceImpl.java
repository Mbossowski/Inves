package com.example.Inves.services.impl;

import com.example.Inves.models.User;
import com.example.Inves.requestmodels.UserRequest;
import com.example.Inves.persistence.repositories.UserDAORepository;
import com.example.Inves.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAORepository userDAORepository;




    public User registerUser(UserRequest userRequest) {
        // Encrypt the password using the password encoder


        // Create a new User using the builder pattern and values from UserRequest
        User newUser = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .Phone(userRequest.getPhone())
                .password(userRequest.getPassword()) // Set the encrypted password
                .build();

        // Save the new user to the database
        return userDAORepository.save(newUser);
    }
}
