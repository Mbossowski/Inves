package com.example.Inves.services;

import com.example.Inves.models.Watchlist;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 11:51
 */
public interface WatchlistService {
    List<Watchlist> getWatchlistByUserId(Long userId);
}
