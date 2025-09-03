package com.crypto.repository;

import com.crypto.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findBySymbolOrderByCreatedAtDesc(String symbol);
    
    @Query("SELECT t FROM Trade t ORDER BY t.createdAt DESC")
    List<Trade> findAllOrderByCreatedAtDesc();
    
    List<Trade> findByStatusOrderByCreatedAtDesc(Trade.TradeStatus status);
}
