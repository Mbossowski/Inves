package com.example.Inves.models;

import jakarta.persistence.Entity;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 28/11/2024 - 12:58
 */
@Entity
public class Transaction {
    private String ID_Transaction;
    private String ID_User;
    private String ID_Stock;
    private String TransactionType ;
    private String Quantity;
    private String PricePerUnit;
    private String TotalAmount;
    private String TransactionDate;
}
