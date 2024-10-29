package com.project.CurrencyConverter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.CurrencyConverter.Service.CurrencyService;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/convert")
    public ResponseEntity<?> convert(@RequestParam String base, @RequestParam String target, @RequestParam double amount) {
        double result = currencyService.convertCurrency(base, target, amount);
        return ResponseEntity.ok(result);
    }
}
