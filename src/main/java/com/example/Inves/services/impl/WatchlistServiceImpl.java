package com.example.Inves.services.impl;

import com.example.Inves.models.Stock;
import com.example.Inves.models.Watchlist;
import com.example.Inves.persistence.repositories.StockDAORepository;
import com.example.Inves.persistence.repositories.WatchlistDAORepository;
import com.example.Inves.services.StockService;
import com.example.Inves.services.WatchlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:26
 */

@Transactional
@Service
public class WatchlistServiceImpl implements WatchlistService {

    private WatchlistDAORepository WatchlistRepository;

    @Autowired
    public WatchlistServiceImpl(WatchlistDAORepository WatchlistRepository) {
        this.WatchlistRepository = WatchlistRepository;
    }

    @Override
    public List<Watchlist> getWatchlistByUserId(Long userId) {
        return WatchlistRepository.findByUserId(userId);
    }
}
