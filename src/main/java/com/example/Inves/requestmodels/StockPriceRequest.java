package com.example.Inves.requestmodels;

import com.example.Inves.models.*;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:00
 */
@Data
public class StockPriceRequest {
    private Stock stock;
    private Date Date;
    private BigDecimal OpenPrice;
    private BigDecimal ClosePrice;
    private BigDecimal HighPrice;
    private BigDecimal LowPrice;
    private Long Volume;
}
