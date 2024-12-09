package com.example.Inves.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

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
    private Long ID_Stock;

    private String Symbol;

    private String CompanyName;

    private String Market;


    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockHolding> stockHoldings = new ArrayList<>();

    @ManyToMany(mappedBy = "stocks")
    private List<Watchlist> watchlists = new ArrayList<>();

    public Stock(Long ID_Stock, String Symbol, String CompanyName, String Market, List<StockHolding> stockHoldings, List<Watchlist> watchlists)
    {
        this.ID_Stock = ID_Stock;
        this.Symbol = Symbol;
        this.CompanyName = CompanyName;
        this.Market = Market;
        this.stockHoldings = stockHoldings;
        this.watchlists = watchlists;
    };

    public Stock(Long ID_Stock, String Symbol, String CompanyName, String Market)
    {
        this.ID_Stock = ID_Stock;
        this.Symbol = Symbol;
        this.CompanyName = CompanyName;
        this.Market = Market;
    };

    public Stock()
    {}

}
