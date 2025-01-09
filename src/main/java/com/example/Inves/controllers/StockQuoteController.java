package com.example.Inves.controllers;


import com.example.Inves.models.StockPrice;
import com.example.Inves.models.StockQuote;
import com.example.Inves.services.StockQuoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 02/01/2025 - 20:18
 */
@RestController
@RequestMapping( value = "/api/stockQuote")
@CrossOrigin("http://localhost:3000")
public class StockQuoteController {

    private final StockQuoteService stockQuoteService;

    @Autowired
    public StockQuoteController(StockQuoteService stockQuoteService) {
        this.stockQuoteService = stockQuoteService;
    }


    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockQuote>> getStockQuotesBySymbolForLastPeriod(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "0") Long periodInDays) {
        List<StockQuote> stockQuotes = new ArrayList<>();
        Long i = periodInDays;
        while(stockQuotes.isEmpty()) {
            stockQuotes = stockQuoteService.getStockQuotesBySymbolForLastPeriod(symbol, periodInDays+i);
            i++;
        }
        if (stockQuotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stockQuotes);
    }



}
