package com.example.Inves.services.impl;

import com.example.Inves.models.StockPrice;
import com.example.Inves.persistence.repositories.StockPriceDAORepository;
import com.example.Inves.services.StockPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:25
 */
@Transactional
@Service
public class StockPriceServiceImpl implements StockPriceService {

    private final StockPriceDAORepository stockPriceRepository;

    @Autowired
    public StockPriceServiceImpl(StockPriceDAORepository stockPriceRepository) {
        this.stockPriceRepository = stockPriceRepository;
    }

    /**
     * Fetch stock prices by symbol from the database for the last 24 hours.
     *
     * @param symbol the stock symbol
     * @return list of stock prices from the last 24 hours
     */
    public List<StockPrice> getStockPricesBySymbolForLastPeriod(String symbol, Long period) {
        LocalDateTime PeriodsAgo = LocalDateTime.now().minusHours(period);
        return stockPriceRepository.findBySymbolAndDatetimeAfterOrderByDatetimeAsc(symbol, PeriodsAgo);
    }



    public List<StockPrice> detectFormation(String symbol, int periodInMinutes) {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        List<StockPrice> prices = stockPriceRepository.findBySymbolAndDatetimeAfterOrderByDatetimeAsc(symbol,twentyFourHoursAgo);

        // Implementacja analizy technicznej (przykład z formacją trójkąta)
        List<StockPrice> formationPoints = new ArrayList<>();
        if (prices.size() >= 3) {
            formationPoints.add(new StockPrice(symbol, prices.get(0).getLastSale(),prices.get(0).getDatetime()));
            formationPoints.add(new StockPrice(symbol, prices.get(prices.size() / 2).getLastSale(), prices.get(prices.size() / 2).getDatetime()));
            formationPoints.add(new StockPrice(symbol, prices.get(prices.size() - 1).getLastSale(), prices.get(prices.size() - 1).getDatetime()));
        }

        return formationPoints;
    }



}
