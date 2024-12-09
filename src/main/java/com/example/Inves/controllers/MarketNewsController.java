package com.example.Inves.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inves.models.MarketNews;
import com.example.Inves.requestmodels.MarketNewsRequest;
import com.example.Inves.services.*;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:19
 */
@RestController
@RequestMapping( value = "/api/marketNews")
@CrossOrigin("http://localhost:3000")
public class MarketNewsController {
}
