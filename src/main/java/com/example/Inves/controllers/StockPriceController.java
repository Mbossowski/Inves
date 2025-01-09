package com.example.Inves.controllers;

import com.example.Inves.persistence.repositories.StockPriceDAORepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inves.models.StockPrice;
import com.example.Inves.requestmodels.StockPriceRequest;
import com.example.Inves.services.*;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 07/12/2024 - 11:53
 */

@RestController
@RequestMapping( value = "/api/stockPrice")
@CrossOrigin("http://localhost:3000")
public class StockPriceController {

    private final StockPriceService stockPriceService;

    @Autowired
    public StockPriceController(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    /**
     * Endpoint to get stock prices by symbol for the last 24 hours.
     *
     * @param symbol the stock symbol
     * @return list of stock prices for the given symbol from the last 24 hours
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockPrice>> getStockPricesBySymbolForLast24Hours(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "24") Long periodInHours) {
        List<StockPrice> stockPrices = stockPriceService.getStockPricesBySymbolForLastPeriod(symbol, periodInHours);

        if (stockPrices.isEmpty()) {
            stockPrices = stockPriceService.getStockPricesBySymbolForLastPeriod(symbol, periodInHours + 24);
            if (stockPrices.isEmpty()) {
            return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(stockPrices);
        }
        return ResponseEntity.ok(stockPrices);
    }

    @GetMapping("/{symbol}/formation")
    public ResponseEntity<List<StockPrice>> getFormationPoints(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "60") int periodInMinutes) {
        List<StockPrice> formationPoints = stockPriceService.detectFormation(symbol, periodInMinutes);
        if (formationPoints.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(formationPoints);
    }
}
