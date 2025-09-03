package com.crypto.service;

import com.crypto.dto.TradeRequest;
import com.crypto.model.Trade;
import com.crypto.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TradeService {
    
    @Autowired
    private TradeRepository tradeRepository;
    
    @Autowired
    private PortfolioService portfolioService;
    
    private final Random random = new Random();
    
    public Trade createTrade(TradeRequest request) {
        Trade trade = new Trade(
            request.getSymbol().toLowerCase(),
            request.getType(),
            request.getAmount(),
            request.getPrice()
        );
        
        Trade savedTrade = tradeRepository.save(trade);
        
        // Simulate trade execution after a short delay
        executeTrade(savedTrade);
        
        return savedTrade;
    }
    
    private void executeTrade(Trade trade) {
        // Simulate 90% success rate
        boolean success = random.nextDouble() < 0.9;
        
        if (success) {
            trade.setStatus(Trade.TradeStatus.COMPLETED);
            trade.setExecutedAt(LocalDateTime.now());
            
            // Update portfolio
            portfolioService.updatePortfolioFromTrade(trade);
        } else {
            trade.setStatus(Trade.TradeStatus.FAILED);
        }
        
        tradeRepository.save(trade);
    }
    
    public List<Trade> getAllTrades() {
        return tradeRepository.findAllOrderByCreatedAtDesc();
    }
    
    public List<Trade> getTradesBySymbol(String symbol) {
        return tradeRepository.findBySymbolOrderByCreatedAtDesc(symbol.toLowerCase());
    }
    
    public Optional<Trade> getTradeById(Long id) {
        return tradeRepository.findById(id);
    }
    
    public List<Trade> getRecentTrades(int limit) {
        List<Trade> allTrades = getAllTrades();
        return allTrades.subList(0, Math.min(limit, allTrades.size()));
    }
    
    public Trade cancelTrade(Long tradeId) {
        Optional<Trade> tradeOpt = tradeRepository.findById(tradeId);
        if (tradeOpt.isPresent()) {
            Trade trade = tradeOpt.get();
            if (trade.getStatus() == Trade.TradeStatus.PENDING) {
                trade.setStatus(Trade.TradeStatus.CANCELLED);
                return tradeRepository.save(trade);
            }
        }
        return null;
    }
}
