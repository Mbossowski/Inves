package com.example.Inves.services;


import com.example.Inves.models.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 11:49
 */

public interface StockService {
    Page<Stock> allStocks(int page, int size);

    Page<Stock> getStocks(String query, Pageable pageable);

    Optional<Stock> getStock(String symbol);

    public Stock getStockDetails(String symbol);
}
