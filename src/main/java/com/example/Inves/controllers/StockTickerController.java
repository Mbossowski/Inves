package com.example.Inves.controllers;

import com.example.Inves.services.impl.StockTickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 27/12/2024 - 18:50
 */
@RestController
public class StockTickerController {

    @Autowired
    private StockTickerService stockTickerService;

    @GetMapping("/tickers")
    public List<String> getTickers(
            @RequestParam(defaultValue = "Nasdaq") String exchange,
            @RequestParam(required = false) Long minMarketCap,
            @RequestParam(required = false) Long maxMarketCap,
            @RequestParam(required = false) String sector
    ) {

        return new ArrayList<>();
    }
}