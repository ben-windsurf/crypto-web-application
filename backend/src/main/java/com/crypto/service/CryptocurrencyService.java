package com.crypto.service;

import com.crypto.dto.CryptoPriceResponse;
import com.crypto.model.Cryptocurrency;
import com.crypto.repository.CryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CryptocurrencyService {
    
    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;
    
    private final Random random = new Random();
    
    // Mock current prices - simulating real market data
    private final Map<String, BigDecimal> basePrices = Map.of(
        "bitcoin", new BigDecimal("43250.00"),
        "ethereum", new BigDecimal("2680.00"),
        "cardano", new BigDecimal("0.52"),
        "solana", new BigDecimal("98.50")
    );
    
    private final Map<String, String> coinNames = Map.of(
        "bitcoin", "Bitcoin",
        "ethereum", "Ethereum",
        "cardano", "Cardano",
        "solana", "Solana"
    );
    
    public Map<String, CryptoPriceResponse> getCurrentPrices() {
        Map<String, CryptoPriceResponse> prices = new HashMap<>();
        
        for (Map.Entry<String, BigDecimal> entry : basePrices.entrySet()) {
            String coinId = entry.getKey();
            BigDecimal basePrice = entry.getValue();
            
            // Add some realistic price volatility (±5%)
            double volatility = (random.nextDouble() - 0.5) * 0.1; // ±5%
            BigDecimal currentPrice = basePrice.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(volatility)));
            currentPrice = currentPrice.setScale(2, RoundingMode.HALF_UP);
            
            // Generate realistic 24h change (±10%)
            double change24h = (random.nextDouble() - 0.5) * 20; // ±10%
            BigDecimal change24hPercent = BigDecimal.valueOf(change24h).setScale(2, RoundingMode.HALF_UP);
            
            CryptoPriceResponse response = new CryptoPriceResponse(
                coinId, 
                coinNames.get(coinId), 
                currentPrice, 
                change24hPercent
            );
            
            prices.put(coinId, response);
            
            // Update database
            updateCryptocurrencyPrice(coinId, coinNames.get(coinId), currentPrice, change24hPercent);
        }
        
        return prices;
    }
    
    public CryptoPriceResponse getPriceBySymbol(String symbol) {
        String coinId = symbol.toLowerCase();
        if (!basePrices.containsKey(coinId)) {
            return null;
        }
        
        BigDecimal basePrice = basePrices.get(coinId);
        double volatility = (random.nextDouble() - 0.5) * 0.1;
        BigDecimal currentPrice = basePrice.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(volatility)));
        currentPrice = currentPrice.setScale(2, RoundingMode.HALF_UP);
        
        double change24h = (random.nextDouble() - 0.5) * 20;
        BigDecimal change24hPercent = BigDecimal.valueOf(change24h).setScale(2, RoundingMode.HALF_UP);
        
        CryptoPriceResponse response = new CryptoPriceResponse(
            coinId, 
            coinNames.get(coinId), 
            currentPrice, 
            change24hPercent
        );
        
        updateCryptocurrencyPrice(coinId, coinNames.get(coinId), currentPrice, change24hPercent);
        
        return response;
    }
    
    private void updateCryptocurrencyPrice(String symbol, String name, BigDecimal price, BigDecimal change24h) {
        Optional<Cryptocurrency> existing = cryptocurrencyRepository.findBySymbol(symbol);
        
        Cryptocurrency crypto;
        if (existing.isPresent()) {
            crypto = existing.get();
            crypto.setCurrentPrice(price);
            crypto.setChangePercent24h(change24h);
            crypto.setLastUpdated(LocalDateTime.now());
        } else {
            crypto = new Cryptocurrency(symbol, name, price, change24h);
        }
        
        cryptocurrencyRepository.save(crypto);
    }
    
    public List<Cryptocurrency> getAllCryptocurrencies() {
        return cryptocurrencyRepository.findAll();
    }
}
