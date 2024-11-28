package com.example.Inves.models;

import jakarta.persistence.Entity;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 28/11/2024 - 12:58
 */

@Entity
public class StockPrice {
    private String ID_Stock_Price;
    private String ID_Stock;
    private String Date;
    private String OpenPrice;
    private String ClosePrice;
    private String HighPrice;
    private String LowPrice;
    private String Volume;

}
