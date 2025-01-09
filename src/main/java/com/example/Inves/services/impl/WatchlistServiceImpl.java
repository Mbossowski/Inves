package com.example.Inves.services.impl;

import com.example.Inves.models.Stock;
import com.example.Inves.models.User;
import com.example.Inves.models.Watchlist;
import com.example.Inves.persistence.repositories.StockDAORepository;
import com.example.Inves.persistence.repositories.UserDAORepository;
import com.example.Inves.persistence.repositories.WatchlistDAORepository;
import com.example.Inves.services.WatchlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class WatchlistServiceImpl implements WatchlistService {

    @Autowired
    private WatchlistDAORepository watchlistRepository;
    @Autowired
    private StockDAORepository stockRepository;
    @Autowired
    private UserDAORepository userRepository;

    @Autowired
    public WatchlistServiceImpl(WatchlistDAORepository WatchlistRepository) {
        this.watchlistRepository = WatchlistRepository;
    }

    @Override
    public List<Watchlist> getWatchlistByUserId(Long userId) {
        return watchlistRepository.findByUserId(userId);
    }

    public String addStockToWatchlist(Long userId, String stockSymbol) throws Exception {
        // Fetch user and stock
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        Stock stock = stockRepository.findBySymbol(stockSymbol).orElseThrow(() -> new Exception("Stock not found"));

        // Check if the stock already exists in the user's watchlist
     //   Optional<Watchlist> existingWatchlist = watchlistRepository.findByUserIdAndSymbol(userId, stockSymbol);
     //   if (existingWatchlist.isPresent()) {
     //       return "This stock is already in your watchlist.";
     //   }

        // Add stock to watchlist if not already present
        Watchlist watchlist = new Watchlist(user, stock);
        watchlistRepository.save(watchlist);
        return "Stock added to your watchlist!";
    }
}
