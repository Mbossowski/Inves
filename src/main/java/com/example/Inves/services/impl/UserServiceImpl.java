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

        if (!userDAORepository.findAllByUsername(userRequest.getUsername()).isEmpty()) {
            // Rzuć wyjątek, jeśli użytkownik o podanej nazwie istnieje
            throw new IllegalArgumentException("User with username '" + userRequest.getUsername() + "' already exists.");
        }
        // Create a new User using the builder pattern and values from UserRequest
        User newUser = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .title(userRequest.getTitle())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .password(userRequest.getPassword()) // Set the encrypted password
                .build();

        // Save the new user to the database
        return userDAORepository.save(newUser);


    }

}
