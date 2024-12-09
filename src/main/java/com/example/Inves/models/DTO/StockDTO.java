package com.example.Inves.models.DTO;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 09/12/2024 - 22:35
 */
public class StockDTO {
    private String symbol;
    private String name;
    private String lastsale;
    private String netchange;
    private String pctchange;
    private String marketCap;

    // Getters and setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastsale() {
        return lastsale;
    }

    public void setLastsale(String lastsale) {
        this.lastsale = lastsale;
    }

    public String getNetchange() {
        return netchange;
    }

    public void setNetchange(String netchange) {
        this.netchange = netchange;
    }

    public String getPctchange() {
        return pctchange;
    }

    public void setPctchange(String pctchange) {
        this.pctchange = pctchange;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }
}