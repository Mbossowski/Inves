package com.example.Inves.models;

import jakarta.persistence.Entity;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 28/11/2024 - 12:58
 */

@Entity
public class Stock {
    private String ID_Stock;
    private String Symbol;
    private String CompanyName;
    private String Market;
}
