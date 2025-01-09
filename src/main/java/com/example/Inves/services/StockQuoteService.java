package com.example.Inves.services;

import com.example.Inves.models.StockQuote;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 02/01/2025 - 20:13
 */
public interface StockQuoteService {

    public List<StockQuote> getStockQuotesBySymbolForLastPeriod(String symbol, Long period);


}
