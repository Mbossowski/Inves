package com.example.Inves.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


/*
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 19/11/2024 - 16:22
 */

@Entity
public class User {
    private String ID_User;
    private String FirstName;
    private String LastName;
    private String Title;
    private String Phone;
    private String Address;
    private String IBAN;



}
