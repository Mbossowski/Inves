package com.example.Inves.scheduling;

import com.example.Inves.services.impl.YahooFinanceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 12/12/2024 - 16:05
 */
@Component
public class StockScheduler {
    private static final Logger logger = LoggerFactory.getLogger(StockScheduler.class);

    private final YahooFinanceServiceImpl yahooFinanceService;

    // Variable to track current page
    private int currentPage = 1; // Start from the first page

    public StockScheduler(YahooFinanceServiceImpl yahooFinanceService) {
        this.yahooFinanceService = yahooFinanceService;
    }

    public void setCurrentPage(int page) {
        this.currentPage = page;
    }

    // Schedule the method to run every minute (or adjust as needed)
    @Scheduled(fixedRate = 60000) // 60,000 ms = 60 sec
    public void fetchAndBroadcastStocks() {
        logger.info("Fetching stock data from Yahoo API for page {}", currentPage);
        try {
            // Fetch the stock data for the current page
            yahooFinanceService.allStocks(String.valueOf(currentPage));

            // After fetching, increment the page to fetch the next page of stocks in the next cycle


            // Optionally, you can add a check here to reset the page if it exceeds the maximum number of pages
            // For example, if you know there are 100 pages, you could reset it back to 1:
            // if (currentPage > 100) currentPage = 1;

            logger.info("Stock data updated successfully for page {}", currentPage);
        } catch (Exception e) {
            logger.error("Error updating stock data for page {}: {}", currentPage, e.getMessage());
        }
    }
}
