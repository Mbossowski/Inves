package com.example.Inves.services.impl;
import com.example.Inves.models.Stock;
import com.example.Inves.persistence.repositories.StockDAORepository;
import com.example.Inves.services.StockService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:25
 */
@Transactional
@Service
public class StockServiceImpl implements StockService {

    private StockDAORepository StockRepository;

    @Autowired
    public StockServiceImpl(StockDAORepository StockRepository) {
        this.StockRepository = StockRepository;
    }
    public List<Stock> allStocks(String Page)
    {
        return StockRepository.findAll();
    }
}
