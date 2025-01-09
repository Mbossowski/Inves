package com.example.Inves.persistence.repositories;


import com.example.Inves.models.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:32
 */
@Repository
public interface StockDAORepository extends JpaRepository<Stock, Long> {
    // Standard pagination
    Page<Stock> findAll(Pageable pageable);

    // Example of a custom query method (if you need filtered results)
    Page<Stock> findBySymbolContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(
            String Symbol, String CompanyName, Pageable pageable);


    // Example: Fetch stocks with a minimum ID (custom requirement)
    Page<Stock> findByIdGreaterThan(Long minId, Pageable pageable);

    // Find stock by symbol
     Optional<Stock> findBySymbol(String symbol);

    // Upsert method
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.lastSale = :lastSale, s.netChange = :netChange, s.pctChange = :pctChange, s.volume = :volume " +
            "WHERE s.symbol = :symbol")
    void updateStockData(String symbol, Double lastSale, Double netChange, Double pctChange, Integer volume);

    // If stock does not exist, insert new stock
    @Modifying
    @Transactional
    @Query("INSERT INTO Stock(symbol, companyName, lastSale, netChange, pctChange, volume, market, country, industry, sector) " +
            "VALUES (:symbol, :companyName, :lastSale, :netChange, :pctChange, :volume, :market, :country, :industry, :sector)")
    void insertStockData(String symbol, String companyName, Double lastSale, Double netChange, Double pctChange, Integer volume,
                         String market, String country, String industry, String sector);

    // Upsert method
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.fiftyTwoWeekHigh = :fiftyTwoWeekHigh, s.fiftyTwoWeekLow = :fiftyTwoWeekLow, s.regularMarketDayHigh = :regularMarketDayHigh, s.regularMarketDayLow = :regularMarketDayLow, s.volume = :volume, s.previousClose = :previousClose " +
            "WHERE s.symbol = :symbol")
    void updateStockDetails(String symbol, Double fiftyTwoWeekHigh, Double fiftyTwoWeekLow, Double regularMarketDayHigh, Double regularMarketDayLow, Integer volume, Double previousClose);

}

