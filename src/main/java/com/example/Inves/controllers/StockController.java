package com.example.Inves.controllers;

import com.example.Inves.services.impl.StockServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inves.models.Stock;
import com.example.Inves.requestmodels.StockRequest;
import com.example.Inves.services.*;
import com.example.Inves.services.impl.*;
import java.util.List;
import java.util.Map;

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
    private YahooFinanceServiceImpl stockService;

    @GetMapping("")
    public List<Stock> allStocks(@RequestParam(required = false, defaultValue = "1") String Page) {
        return stockService.allStocks(Page);
    }
}
