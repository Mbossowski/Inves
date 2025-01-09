package com.example.Inves.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor  // Generates a constructor with all fields
@NoArgsConstructor   // Generates a no-argument constructor
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "symbol", referencedColumnName = "symbol", nullable = false)
    private Stock stock;


    public Watchlist(User user, Stock stock)
    {
        this.user = user;
        this.stock = stock;

    }
}
