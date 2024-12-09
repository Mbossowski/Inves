package com.example.Inves.requestmodels;

import com.example.Inves.models.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:01
 */
@Data
public class TransactionRequest {
    private User user;
    private Stock stock;
    private String TransactionType ;
    private int Quantity;
    private BigDecimal PricePerUnit;
    private BigDecimal  TotalAmount;
    private Date TransactionDate;
}
