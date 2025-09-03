package com.crypto.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
public class Trade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String symbol;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType type;
    
    @Column(precision = 20, scale = 8, nullable = false)
    private BigDecimal amount;
    
    @Column(precision = 20, scale = 8, nullable = false)
    private BigDecimal price;
    
    @Column(precision = 20, scale = 8, nullable = false)
    private BigDecimal totalValue;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeStatus status;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime executedAt;
    
    // Constructors
    public Trade() {}
    
    public Trade(String symbol, TradeType type, BigDecimal amount, BigDecimal price) {
        this.symbol = symbol;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.totalValue = amount.multiply(price);
        this.status = TradeStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public TradeType getType() { return type; }
    public void setType(TradeType type) { this.type = type; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }
    
    public TradeStatus getStatus() { return status; }
    public void setStatus(TradeStatus status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(LocalDateTime executedAt) { this.executedAt = executedAt; }
    
    public enum TradeType {
        BUY, SELL
    }
    
    public enum TradeStatus {
        PENDING, COMPLETED, CANCELLED, FAILED
    }
}
