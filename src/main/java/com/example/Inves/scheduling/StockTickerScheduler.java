package com.example.Inves.scheduling;


import com.example.Inves.models.Stock;
import com.example.Inves.models.StockPrice;
import com.example.Inves.persistence.repositories.StockDAORepository;
import com.example.Inves.persistence.repositories.StockPriceDAORepository;
import com.example.Inves.services.impl.StockTickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 27/12/2024 - 18:51
 */
@Component
public class StockTickerScheduler {

    @Autowired
    private StockTickerService stockTickerService;

    @Autowired
    private StockDAORepository stockRepository;

    @Autowired
    private StockPriceDAORepository stockPriceRepository;



    @Scheduled(fixedRate = 60000) // Every minute
    public void fetchStockData() {
        if (!isMarketOpen() && !stockRepository.findAll().isEmpty()) {
            return; // Exit if market is closed
        }

        // Fetch the stock data (you can replace this with your logic for fetching data from the API)
        // Assuming you have a list of stocks to process (you can replace this with actual data)
        List<Stock> stocks = stockTickerService.getStockTickers("nasdaq", null, null, null);

        LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);

        // Loop through each stock and upsert it
            for (Stock stock : stocks) {
                Optional<Stock> existingStock = stockRepository.findBySymbol(stock.getSymbol());

                if (existingStock.isPresent()) {
                    // Update existing stock
                    stockRepository.updateStockData(stock.getSymbol(), stock.getLastSale(), stock.getNetChange(),
                            stock.getPctChange(), stock.getVolume());
                } else {
                    // Insert new stock
                    stockRepository.insertStockData(stock.getSymbol(), stock.getCompanyName(), stock.getLastSale(),
                            stock.getNetChange(), stock.getPctChange(), stock.getVolume(), stock.getMarket(),
                            stock.getCountry(), stock.getIndustry(), stock.getSector());
                }


                StockPrice newStockPrice = new StockPrice(stock.getSymbol(), stock.getLastSale(), currentDateTime );
                stockPriceRepository.save(newStockPrice);


            }
        }

    private boolean isMarketOpen() {
        ZoneId marketZone = ZoneId.of("America/New_York");
        ZonedDateTime now = ZonedDateTime.now(marketZone);
        int hour = now.getHour();
        int minute = now.getMinute();

        // Market opens at 9:30 AM ET and closes at 4:00 PM ET
        return (hour > 9 || (hour == 9 && minute >= 30)) && hour < 16;
    }

    }