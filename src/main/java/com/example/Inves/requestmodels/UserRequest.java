package com.example.Inves.requestmodels;

import com.example.Inves.models.*;
import lombok.Data;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:02
 */
@Data
public class UserRequest {
    private String FirstName;
    private String LastName;
    private String Title;
    private String Phone;
    private String Address;
    private String IBAN;
}
