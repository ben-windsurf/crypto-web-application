package com.crypto.controller;

import com.crypto.dto.CryptoPriceResponse;
import com.crypto.model.Cryptocurrency;
import com.crypto.service.CryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v3")
@CrossOrigin(origins = "*")
public class CryptocurrencyController {
    
    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    
    @GetMapping("/simple/price")
    public ResponseEntity<Map<String, CryptoPriceResponse>> getCurrentPrices(
            @RequestParam String ids,
            @RequestParam(name = "vs_currencies") String vsCurrencies,
            @RequestParam(name = "include_24hr_change", defaultValue = "false") boolean include24hrChange) {
        
        Map<String, CryptoPriceResponse> prices = cryptocurrencyService.getCurrentPrices();
        return ResponseEntity.ok(prices);
    }
    
    @GetMapping("/price/{symbol}")
    public ResponseEntity<CryptoPriceResponse> getPriceBySymbol(@PathVariable String symbol) {
        CryptoPriceResponse price = cryptocurrencyService.getPriceBySymbol(symbol);
        if (price != null) {
            return ResponseEntity.ok(price);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/cryptocurrencies")
    public ResponseEntity<List<Cryptocurrency>> getAllCryptocurrencies() {
        List<Cryptocurrency> cryptos = cryptocurrencyService.getAllCryptocurrencies();
        return ResponseEntity.ok(cryptos);
    }
}
