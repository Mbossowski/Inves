package com.example.Inves.persistence.repositories;

import com.example.Inves.models.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:31
 */
@Repository
public interface StockPriceDAORepository extends JpaRepository<StockPrice, Long> {
    /**
     * Find stock prices by symbol and datetime after a given date, ordered by datetime ascending.
     *
     * @param symbol the stock symbol
     * @param datetime the time threshold
     * @return list of stock prices ordered by datetime
     */
    List<StockPrice> findBySymbolAndDatetimeAfterOrderByDatetimeAsc(String symbol, LocalDateTime datetime);

    @Query("SELECT sp FROM StockPrice sp WHERE sp.datetime = (SELECT MAX(s.datetime) FROM StockPrice s WHERE s.symbol = :symbol)")
    StockPrice findLatestBySymbol(String symbol);



}
