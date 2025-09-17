package com.crypto.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cryptocurrencies")
public class Cryptocurrency {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String symbol;
    
    @Column(nullable = false)
    private String name;
    
    @Column(precision = 20, scale = 8)
    private BigDecimal currentPrice;
    
    @Column(precision = 10, scale = 4)
    private BigDecimal change24h;
    
    @Column(precision = 10, scale = 4)
    private BigDecimal changePercent24h;
    
    @Column(nullable = false)
    private LocalDateTime lastUpdated;
    
    // Constructors
    public Cryptocurrency() {}
    
    public Cryptocurrency(String symbol, String name, BigDecimal currentPrice, 
                         BigDecimal changePercent24h) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.changePercent24h = changePercent24h;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
    
    public BigDecimal getChange24h() { return change24h; }
    public void setChange24h(BigDecimal change24h) { this.change24h = change24h; }
    
    public BigDecimal getChangePercent24h() { return changePercent24h; }
    public void setChangePercent24h(BigDecimal changePercent24h) { this.changePercent24h = changePercent24h; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
