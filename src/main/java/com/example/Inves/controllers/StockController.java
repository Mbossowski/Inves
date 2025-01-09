package com.example.Inves.controllers;

import com.example.Inves.models.Stock;
import com.example.Inves.models.StockPrice;
import com.example.Inves.services.StockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.Inves.services.impl.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 07/12/2024 - 11:53
 */
@RestController
@RequestMapping( value = "/api/stock")
@CrossOrigin("http://localhost:3000")
public class StockController {

    @Autowired
    private StockService stockService;


    @GetMapping()
    public ResponseEntity<Page<Stock>> getStocks(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Stock> stocks = stockService.getStocks(query, pageable);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/details")
    public ResponseEntity<Stock> getStockDetails(
            @RequestParam(required = false, defaultValue = "") String symbol
    )
    {
        Stock stock = stockService.getStockDetails(symbol);

        return ResponseEntity.ok(stock);
    }

}
