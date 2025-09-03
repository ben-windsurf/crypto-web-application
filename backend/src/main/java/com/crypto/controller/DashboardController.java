package com.crypto.controller;

import com.crypto.dto.CryptoPriceResponse;
import com.crypto.model.Portfolio;
import com.crypto.model.Trade;
import com.crypto.service.CryptocurrencyService;
import com.crypto.service.PortfolioService;
import com.crypto.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    
    @Autowired
    private PortfolioService portfolioService;
    
    @Autowired
    private TradeService tradeService;
    
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getDashboardOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        // Get current prices
        Map<String, CryptoPriceResponse> prices = cryptocurrencyService.getCurrentPrices();
        overview.put("prices", prices);
        
        // Get portfolio
        List<Portfolio> portfolio = portfolioService.getPortfolio();
        overview.put("portfolio", portfolio);
        
        // Get total portfolio value
        BigDecimal totalValue = portfolioService.getTotalPortfolioValue();
        overview.put("totalPortfolioValue", totalValue);
        
        // Get recent transactions
        List<Trade> recentTrades = tradeService.getRecentTrades(5);
        overview.put("recentTrades", recentTrades);
        
        return ResponseEntity.ok(overview);
    }
}
