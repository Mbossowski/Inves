package com.example.Inves.requestmodels;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String title;
    private String phone;
    private String country;
    private String city;
    private String street;
    private String iban;
    private String email;
    private String password;

}
