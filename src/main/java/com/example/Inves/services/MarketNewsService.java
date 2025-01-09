package com.example.Inves.services;

import com.example.Inves.models.MarketNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 11:50
 */
public interface MarketNewsService {
    public List<MarketNews> getStockNews(String symbol, int offset, int limit);
}
