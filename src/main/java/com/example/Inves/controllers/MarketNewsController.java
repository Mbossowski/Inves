package com.example.Inves.controllers;

import com.example.Inves.models.Stock;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inves.models.MarketNews;
import com.example.Inves.requestmodels.MarketNewsRequest;
import com.example.Inves.services.*;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:19
 */
@RestController
@RequestMapping( value = "/api/marketNews")
@CrossOrigin("http://localhost:3000")
public class MarketNewsController {

    @Autowired
    private MarketNewsService marketNewsService;

    @GetMapping()
    public ResponseEntity<List<MarketNews>> getStockNews(
            @RequestParam(required = false, defaultValue = "") String symbol,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<MarketNews> news = marketNewsService.getStockNews(symbol, page*size, size);
        return ResponseEntity.ok(news);
    }
}
