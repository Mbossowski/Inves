package com.example.Inves.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 28/11/2024 - 12:58
 */

@Data
@Builder
@Entity
public class StockPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "stockId")
    private Stock stock;
    private Date Date;
    private BigDecimal OpenPrice;
    private BigDecimal ClosePrice;
    private BigDecimal HighPrice;
    private BigDecimal LowPrice;
    private Long Volume;

}
