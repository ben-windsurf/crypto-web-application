package com.crypto.dto;

import java.math.BigDecimal;

public class CryptoPriceResponse {
    private String symbol;
    private String name;
    private BigDecimal usd;
    private BigDecimal usd_24h_change;
    
    public CryptoPriceResponse() {}
    
    public CryptoPriceResponse(String symbol, String name, BigDecimal usd, BigDecimal usd_24h_change) {
        this.symbol = symbol;
        this.name = name;
        this.usd = usd;
        this.usd_24h_change = usd_24h_change;
    }
    
    // Getters and Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public BigDecimal getUsd() { return usd; }
    public void setUsd(BigDecimal usd) { this.usd = usd; }
    
    public BigDecimal getUsd_24h_change() { return usd_24h_change; }
    public void setUsd_24h_change(BigDecimal usd_24h_change) { this.usd_24h_change = usd_24h_change; }
}
