package com.example.Inves.controllers;

import com.example.Inves.scheduling.StockScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 12/12/2024 - 17:00
 */

@RestController
@RequestMapping("/api")
public class StockSchedulerController {
    @Autowired
    private StockScheduler stockScheduler;

    @PostMapping("/setPage")
    public ResponseEntity<String> setPage(@RequestBody int page) {
        stockScheduler.setCurrentPage(page);  // Method to set the current page
        return ResponseEntity.ok("Page set to " + page);
    }
}
