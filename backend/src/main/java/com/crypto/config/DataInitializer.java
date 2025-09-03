package com.crypto.config;

import com.crypto.model.Portfolio;
import com.crypto.model.Trade;
import com.crypto.repository.PortfolioRepository;
import com.crypto.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
    private TradeRepository tradeRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize sample portfolio data
        if (portfolioRepository.count() == 0) {
            Portfolio btcHolding = new Portfolio("bitcoin", new BigDecimal("0.5"), new BigDecimal("42000.00"));
            Portfolio ethHolding = new Portfolio("ethereum", new BigDecimal("2.3"), new BigDecimal("2650.00"));
            
            portfolioRepository.save(btcHolding);
            portfolioRepository.save(ethHolding);
        }
        
        // Initialize sample trade history
        if (tradeRepository.count() == 0) {
            Trade trade1 = new Trade("bitcoin", Trade.TradeType.BUY, new BigDecimal("0.1"), new BigDecimal("43200.00"));
            trade1.setStatus(Trade.TradeStatus.COMPLETED);
            trade1.setCreatedAt(LocalDateTime.now().minusDays(2));
            trade1.setExecutedAt(LocalDateTime.now().minusDays(2).plusMinutes(1));
            
            Trade trade2 = new Trade("ethereum", Trade.TradeType.SELL, new BigDecimal("0.5"), new BigDecimal("2675.00"));
            trade2.setStatus(Trade.TradeStatus.COMPLETED);
            trade2.setCreatedAt(LocalDateTime.now().minusDays(1));
            trade2.setExecutedAt(LocalDateTime.now().minusDays(1).plusMinutes(2));
            
            Trade trade3 = new Trade("cardano", Trade.TradeType.BUY, new BigDecimal("1000"), new BigDecimal("0.51"));
            trade3.setStatus(Trade.TradeStatus.PENDING);
            trade3.setCreatedAt(LocalDateTime.now().minusHours(2));
            
            tradeRepository.save(trade1);
            tradeRepository.save(trade2);
            tradeRepository.save(trade3);
        }
    }
}
