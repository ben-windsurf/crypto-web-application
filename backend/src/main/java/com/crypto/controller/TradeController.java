package com.crypto.controller;

import com.crypto.dto.TradeRequest;
import com.crypto.model.Trade;
import com.crypto.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trades")
@CrossOrigin(origins = "*")
public class TradeController {
    
    @Autowired
    private TradeService tradeService;
    
    @PostMapping
    public ResponseEntity<Trade> createTrade(@Valid @RequestBody TradeRequest request) {
        Trade trade = tradeService.createTrade(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(trade);
    }
    
    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        return ResponseEntity.ok(trades);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<Trade>> getRecentTrades(@RequestParam(defaultValue = "10") int limit) {
        List<Trade> trades = tradeService.getRecentTrades(limit);
        return ResponseEntity.ok(trades);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable Long id) {
        Optional<Trade> trade = tradeService.getTradeById(id);
        return trade.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<List<Trade>> getTradesBySymbol(@PathVariable String symbol) {
        List<Trade> trades = tradeService.getTradesBySymbol(symbol);
        return ResponseEntity.ok(trades);
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Trade> cancelTrade(@PathVariable Long id) {
        Trade cancelledTrade = tradeService.cancelTrade(id);
        if (cancelledTrade != null) {
            return ResponseEntity.ok(cancelledTrade);
        }
        return ResponseEntity.notFound().build();
    }
}
