package com.example.Inves.models;

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
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Watchlist;

    @ManyToOne
    @JoinColumn(name = "ID_User")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "watchlist_stock",
            joinColumns = @JoinColumn(name = "ID_Watchlist"),
            inverseJoinColumns = @JoinColumn(name = "ID_Stock")
    )
    private List<Stock> stocks = new ArrayList<>();

    private String AddedDate;
}
