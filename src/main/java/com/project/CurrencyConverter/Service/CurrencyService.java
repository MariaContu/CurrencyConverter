package com.project.CurrencyConverter.Service;

import java.util.Map;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    private final String apiKey = "4f17f59eb8e3e9f8584cac4d33a1e722";
    private final RestTemplate restTemplate;

    // Construtor para injetar o RestTemplate e obter a chave da API
    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double convertCurrency(String base, String target, double amount) {
    
        String urlBaseToEur = "https://api.exchangeratesapi.io/v1/latest?access_key=" + apiKey + "&symbols=" + base;
        ResponseEntity<Map<String, Object>> responseBaseToEur = restTemplate.exchange(
            urlBaseToEur,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );
    
        Map<String, Object> bodyBaseToEur = responseBaseToEur.getBody();
        if (bodyBaseToEur == null || !(boolean) bodyBaseToEur.get("success")) {
            throw new RuntimeException("Erro na API externa: " + bodyBaseToEur);
        }
    
        @SuppressWarnings("unchecked")
        Map<String, Object> ratesBaseToEur = (Map<String, Object>) bodyBaseToEur.get("rates");
        if (ratesBaseToEur == null || ratesBaseToEur.get(base) == null) {
            throw new RuntimeException("A moeda de origem não foi encontrada na resposta da API.");
        }
    
        double rateBaseToEur = ((Number) ratesBaseToEur.get(base)).doubleValue();
    
        String urlEurToTarget = "https://api.exchangeratesapi.io/v1/latest?access_key=" + apiKey + "&symbols=" + target;
        ResponseEntity<Map<String, Object>> responseEurToTarget = restTemplate.exchange(
            urlEurToTarget,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );
    
        Map<String, Object> bodyEurToTarget = responseEurToTarget.getBody();
        if (bodyEurToTarget == null || !(boolean) bodyEurToTarget.get("success")) {
            throw new RuntimeException("Erro na API externa: " + bodyEurToTarget);
        }
    
        @SuppressWarnings("unchecked")
        Map<String, Object> ratesEurToTarget = (Map<String, Object>) bodyEurToTarget.get("rates");
        if (ratesEurToTarget == null || ratesEurToTarget.get(target) == null) {
            throw new RuntimeException("A moeda de destino não foi encontrada na resposta da API.");
        }
    
        double rateEurToTarget = ((Number) ratesEurToTarget.get(target)).doubleValue();
    
        double amountInEur = amount / rateBaseToEur;
        double finalValue = amountInEur * rateEurToTarget;
    
        return finalValue;
    }
    
    
}
