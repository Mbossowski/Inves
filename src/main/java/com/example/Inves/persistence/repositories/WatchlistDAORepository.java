package com.example.Inves.persistence.repositories;

import com.example.Inves.models.Stock;
import com.example.Inves.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:32
 */
@Repository
public interface WatchlistDAORepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserId(Long userId); // Find all watchlist items for a specific user

    // Find if the stock already exists in the user's watchlist
  //  Optional<Watchlist> findByUserIdAndStock(Long userId, Stock stock);
}
