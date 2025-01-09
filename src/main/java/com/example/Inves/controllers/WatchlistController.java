package com.example.Inves.controllers;

import com.example.Inves.requestmodels.StockRequest;
import com.example.Inves.services.impl.WatchlistServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inves.models.Watchlist;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 07/12/2024 - 11:53
 */
@RestController
@RequestMapping(value = "/api/watchlist")
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

    @PutMapping("")
    public ResponseEntity<String> addToWatchlist(@RequestParam Long userId, @RequestBody StockRequest stockRequest) {
        try {
            // Call the service method to add the stock
            String response = watchlistService.addStockToWatchlist(userId, stockRequest.getSymbol());

            // Check the response message from the service
            if (response.equals("This stock is already in your watchlist.")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return Bad Request if already in watchlist
            }

            return ResponseEntity.ok(response); // Stock added successfully
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding stock to watchlist.");
        }
    }
}
