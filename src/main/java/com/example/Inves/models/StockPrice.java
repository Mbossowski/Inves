package com.example.Inves.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.time.LocalDateTime;


/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 28/11/2024 - 12:58
 */

@Data
@Builder
@Entity
@AllArgsConstructor  // Generates a constructor with all fields
@NoArgsConstructor   // Generates a no-argument constructor
public class StockPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String symbol;
    private Double lastSale	;
    private LocalDateTime datetime;


    @ManyToOne
    @JoinColumn(name = "symbol", referencedColumnName = "symbol", insertable = false, updatable = false)
    @JsonIgnore
    private Stock stock;


    public StockPrice(String symbol, Double lastSale, LocalDateTime datetime) {
        this.symbol = symbol;
        this.lastSale = lastSale;
        this.datetime = datetime;
    }
}
