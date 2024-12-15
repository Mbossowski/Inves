package com.example.Inves.models;

import jakarta.persistence.*;
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
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Symbol;
    private String CompanyName;
    private String Market;

    private BigDecimal Price;
    private BigDecimal ValueChange;
    private BigDecimal PercentageChange;
    private BigDecimal MarketCap;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockHolding> stockHoldings = new ArrayList<>();



    // Default constructor
    public Stock() {}

    // Constructor with all fields
    public Stock(Long ID_Stock, String Symbol, String CompanyName, String Market, BigDecimal Price, BigDecimal ValueChange, BigDecimal PercentageChange, BigDecimal MarketCap, List<StockHolding> stockHoldings) {
        this.id = ID_Stock;
        this.Symbol = Symbol;
        this.CompanyName = CompanyName;
        this.Market = Market;
        this.Price = Price;
        this.ValueChange = ValueChange;
        this.PercentageChange = PercentageChange;
        this.MarketCap = MarketCap;
        this.stockHoldings = stockHoldings != null ? stockHoldings : new ArrayList<>();

    }

    // Constructor without List<StockHolding> and List<Watchlist>
    public Stock(Long ID_Stock, String Symbol, String CompanyName, String Market, BigDecimal price, BigDecimal change, BigDecimal percentageChange, BigDecimal MarketCap) {
        this.id = ID_Stock;
        this.Symbol = Symbol;
        this.CompanyName = CompanyName;
        this.Market = Market;
        this.Price = Price;
        this.ValueChange = ValueChange;
        this.PercentageChange = PercentageChange;
        this.MarketCap = MarketCap;
    }
}
