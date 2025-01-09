package com.example.Inves.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;
import java.util.*;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 28/11/2024 - 12:58
 */
@Data
@Entity
@Builder
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true, nullable = false)
    private String symbol;

    private String companyName;
    private Double lastSale;
    private Double netChange;
    private Double pctChange;
    private Double fiftyTwoWeekHigh = 0.00;
    private Double fiftyTwoWeekLow = 0.00;
    private Double regularMarketDayHigh = 0.00;
    private Double regularMarketDayLow = 0.00;
    private Double previousClose = 0.00;
    private Integer volume;
    private String market;
    private String country;
    private String industry;
    private String sector;



    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StockPrice> stockPrices = new HashSet<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StockQuote> stockQuotes = new HashSet<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Watchlist> watchlists = new HashSet<>();
    // Default constructor
    public Stock() {}

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public Stock(Long id, String symbol, String companyName, Double lastSale, Double netChange, Double pctChange, Double fiftyTwoWeekHigh, Double fiftyTwoWeekLow, Double regularMarketDayHigh, Double regularMarketDayLow, Double previousClose, Integer volume, String market, String country, String industry, String sector) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.lastSale = lastSale;
        this.netChange = netChange;
        this.pctChange = pctChange;
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
        this.regularMarketDayHigh = regularMarketDayHigh;
        this.regularMarketDayLow = regularMarketDayLow;
        this.previousClose = previousClose;
        this.volume = volume;
        this.market = market;
        this.country = country;
        this.industry = industry;
        this.sector = sector;
    }

    public Stock(String symbol, String companyName, Double lastSale, Double netChange, Double pctChange, Integer volume, Double marketCap, String market, String country, String industry, String ssector) {
     this.symbol = symbol;
     this.companyName = companyName;
     this.lastSale = lastSale;
     this.netChange = netChange;
     this.pctChange = pctChange;
     this.volume = volume;
     this.market = market;
     this.country = country;
     this.industry = industry;
     this.sector = ssector;
     this.fiftyTwoWeekHigh  = 0.00;
     this.fiftyTwoWeekLow  = 0.00;
     this.regularMarketDayHigh  = 0.00;
     this.regularMarketDayLow  = 0.00;
     this.previousClose  = 0.00;

    }

    public void addStockPrice(StockPrice stockPrice) {
        stockPrices.add(stockPrice);
        stockPrice.setStock(this);
    }

    public void removeStockPrice(StockPrice stockPrice) {
        stockPrices.remove(stockPrice);
        stockPrice.setStock(null);
    }
}
