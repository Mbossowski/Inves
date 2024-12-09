package com.example.Inves.services.impl;

import com.example.Inves.models.MarketNews;
import com.example.Inves.services.MarketNewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:24
 */
@Transactional
@Service
public class MarketNewsServiceImpl implements MarketNewsService {
}