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
    private String currency;
    private String exchange;
    private String mic_code;
    private String country;
    private String type;
    private String figi_code;

    // Getter and Setter for symbol
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for currency
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Getter and Setter for exchange
    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    // Getter and Setter for mic_code
    public String getMic_code() {
        return mic_code;
    }

    public void setMic_code(String mic_code) {
        this.mic_code = mic_code;
    }

    // Getter and Setter for country
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and Setter for figi_code
    public String getFigi_code() {
        return figi_code;
    }

    public void setFigi_code(String figi_code) {
        this.figi_code = figi_code;
    }
}