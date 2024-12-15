package com.example.Inves.controllers;

import com.example.Inves.models.Stock;
import com.example.Inves.services.impl.WatchlistServiceImpl;
import com.example.Inves.services.impl.YahooFinanceServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inves.models.Watchlist;
import com.example.Inves.requestmodels.WatchlistRequest;
import com.example.Inves.services.*;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 07/12/2024 - 11:53
 */
@RestController
@RequestMapping( value = "/api/watchlist")
@CrossOrigin("http://localhost:3000")
public class WatchlistController {
    @Autowired
    private WatchlistServiceImpl watchlistService;

    @Autowired
    public WatchlistController(WatchlistServiceImpl watchlistService) {
        this.watchlistService = watchlistService;
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<List<Watchlist>> getWatchlistByUserId(@PathVariable Long idUser) {
        List<Watchlist> watchlists = watchlistService.getWatchlistByUserId(idUser);
        return ResponseEntity.ok(watchlists);
    }
}
