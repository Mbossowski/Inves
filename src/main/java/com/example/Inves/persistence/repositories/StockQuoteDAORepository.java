package com.example.Inves.persistence.repositories;


import com.example.Inves.models.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 02/01/2025 - 20:12
 */
public interface StockQuoteDAORepository extends JpaRepository<StockQuote, Long> {

    List<StockQuote> findBySymbolAndDateAfter(String symbol, Date datetime);
}
