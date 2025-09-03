package com.crypto.service;

import com.crypto.dto.CryptoPriceResponse;
import com.crypto.model.Portfolio;
import com.crypto.model.Trade;
import com.crypto.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    
    public List<Portfolio> getPortfolio() {
        List<Portfolio> portfolio = portfolioRepository.findAll();
        
        // Update current values with latest prices
        for (Portfolio holding : portfolio) {
            updateCurrentValue(holding);
        }
        
        return portfolio;
    }
    
    public Portfolio getPortfolioBySymbol(String symbol) {
        Optional<Portfolio> portfolio = portfolioRepository.findBySymbol(symbol.toLowerCase());
        if (portfolio.isPresent()) {
            Portfolio holding = portfolio.get();
            updateCurrentValue(holding);
            return holding;
        }
        return null;
    }
    
    public BigDecimal getTotalPortfolioValue() {
        List<Portfolio> portfolio = getPortfolio();
        return portfolio.stream()
                .map(Portfolio::getCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void updatePortfolioFromTrade(Trade trade) {
        Optional<Portfolio> existingHolding = portfolioRepository.findBySymbol(trade.getSymbol());
        
        if (existingHolding.isPresent()) {
            Portfolio holding = existingHolding.get();
            updateExistingHolding(holding, trade);
        } else {
            createNewHolding(trade);
        }
    }
    
    private void updateExistingHolding(Portfolio holding, Trade trade) {
        BigDecimal currentQuantity = holding.getQuantity();
        BigDecimal currentAvgPrice = holding.getAveragePrice();
        BigDecimal tradeAmount = trade.getAmount();
        BigDecimal tradePrice = trade.getPrice();
        
        if (trade.getType() == Trade.TradeType.BUY) {
            // Calculate new average price for buy orders
            BigDecimal totalValue = currentQuantity.multiply(currentAvgPrice)
                    .add(tradeAmount.multiply(tradePrice));
            BigDecimal newQuantity = currentQuantity.add(tradeAmount);
            BigDecimal newAvgPrice = totalValue.divide(newQuantity, 8, RoundingMode.HALF_UP);
            
            holding.setQuantity(newQuantity);
            holding.setAveragePrice(newAvgPrice);
        } else {
            // For sell orders, reduce quantity but keep average price
            BigDecimal newQuantity = currentQuantity.subtract(tradeAmount);
            if (newQuantity.compareTo(BigDecimal.ZERO) <= 0) {
                // If selling all or more than owned, remove the holding
                portfolioRepository.delete(holding);
                return;
            }
            holding.setQuantity(newQuantity);
        }
        
        updateCurrentValue(holding);
        holding.setLastUpdated(LocalDateTime.now());
        portfolioRepository.save(holding);
    }
    
    private void createNewHolding(Trade trade) {
        if (trade.getType() == Trade.TradeType.BUY) {
            Portfolio newHolding = new Portfolio(
                trade.getSymbol(),
                trade.getAmount(),
                trade.getPrice()
            );
            updateCurrentValue(newHolding);
            portfolioRepository.save(newHolding);
        }
        // Ignore sell orders for assets not in portfolio
    }
    
    private void updateCurrentValue(Portfolio holding) {
        // Get current market price
        CryptoPriceResponse priceResponse = cryptocurrencyService.getPriceBySymbol(holding.getSymbol());
        if (priceResponse != null) {
            BigDecimal currentMarketPrice = priceResponse.getUsd();
            BigDecimal currentValue = holding.getQuantity().multiply(currentMarketPrice);
            holding.setCurrentValue(currentValue);
        } else {
            // Fallback to average price if market price unavailable
            holding.setCurrentValue(holding.getQuantity().multiply(holding.getAveragePrice()));
        }
    }
}
