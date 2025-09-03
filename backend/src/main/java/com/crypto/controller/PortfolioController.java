package com.crypto.controller;

import com.crypto.model.Portfolio;
import com.crypto.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "*")
public class PortfolioController {
    
    @Autowired
    private PortfolioService portfolioService;
    
    @GetMapping
    public ResponseEntity<List<Portfolio>> getPortfolio() {
        List<Portfolio> portfolio = portfolioService.getPortfolio();
        return ResponseEntity.ok(portfolio);
    }
    
    @GetMapping("/total-value")
    public ResponseEntity<BigDecimal> getTotalPortfolioValue() {
        BigDecimal totalValue = portfolioService.getTotalPortfolioValue();
        return ResponseEntity.ok(totalValue);
    }
    
    @GetMapping("/{symbol}")
    public ResponseEntity<Portfolio> getPortfolioBySymbol(@PathVariable String symbol) {
        Portfolio holding = portfolioService.getPortfolioBySymbol(symbol);
        if (holding != null) {
            return ResponseEntity.ok(holding);
        }
        return ResponseEntity.notFound().build();
    }
}
