package com.example.Inves.requestmodels;

import com.example.Inves.models.*;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 11:58
 */
@Data
public class StockHoldingRequest {
    private Portfolio portfolio;
    private Stock stock;
    private int Quantity;
    private BigDecimal AveragePurchasePrice;
}
