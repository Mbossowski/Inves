package com.example.Inves.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 02/01/2025 - 20:08
 */

@Data
@Builder
@Entity
@AllArgsConstructor  // Generates a constructor with all fields
@NoArgsConstructor   // Generates a no-argument constructor
public class StockQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String symbol;

    private Date date;
    private Double lastClose;
    private Double lastOpen;
    private Double lastHigh;
    private Double lastLow;
    private Long volume;

    @ManyToOne
    @JoinColumn(name = "symbol", referencedColumnName = "symbol", insertable = false, updatable = false)
    @JsonIgnore
    private Stock stock;

    public StockQuote( String symbol, Date date, Double lastClose, Double lastOpen, Double lastHigh, Double lastLow, Long volume)
    {
        this.symbol = symbol;
        this.date = date;
        this.lastOpen = lastOpen;
        this.lastClose = lastClose;
        this.lastHigh = lastHigh;
        this.lastLow = lastLow;
        this.volume = volume;
    }
}
