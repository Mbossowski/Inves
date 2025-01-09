package com.example.Inves.requestmodels;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 02/01/2025 - 20:13
 */

@Data
public class StockQuoteRequest {
    private String symbol;
    private Double lastSale;
    private Date date;
    private Double lastOpen;
    private Double lastHigh;
    private Double lastLow;
}
