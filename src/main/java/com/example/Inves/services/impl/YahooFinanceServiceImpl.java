package com.example.Inves.services.impl;

import com.example.Inves.controllers.WebSocketController;
import com.example.Inves.models.Stock;
import com.example.Inves.models.DTO.StockDTO;
import com.example.Inves.services.YahooFinanceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class YahooFinanceServiceImpl implements YahooFinanceService {

    @Value("${rapidapi.key}")
    private String apiKey;
    private static final Logger logger = LoggerFactory.getLogger(YahooFinanceServiceImpl.class);
    private final WebSocketController webSocketController; // Inject WebSocketController
    private final ObjectMapper objectMapper;

    private long YahooApiRequests =0;
    @Autowired
    public YahooFinanceServiceImpl(ObjectMapper objectMapper, WebSocketController webSocketController) {
        this.objectMapper = objectMapper;
        this.webSocketController = webSocketController;
    }




    public List<Stock> allStocks(String page) {
        String baseUrl = "https://api.twelvedata.com/stocks?exchange=NASDAQ";
        String url = baseUrl;

        List<Stock> stocks = new ArrayList<>();

        try {
            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                  //  .header("&apikey", "2bb91409c3124a27b3461e0102cfb917")
                   // .header("&interval", "1min")
                    .GET()
                    .build();

            // Send request and get response
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Parse response into DTO list
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode dataNode = rootNode.path("data");

            if (dataNode.isArray()) {

                for (JsonNode stockNode : dataNode) {
                    StockDTO stockDTO = objectMapper.treeToValue(stockNode, StockDTO.class);

                    Stock stock = new Stock();
                    stock.setSymbol(stockDTO.getSymbol());
                    stock.setCompanyName(stockDTO.getName());
                    stock.setMarket(stockDTO.getExchange());


                    stocks.add(stock);
                }
            }

            // Send the updated stock list to clients via WebSocket
            String stockUpdateJson = objectMapper.writeValueAsString(stocks); // Convert stock list to JSON
            webSocketController.sendStockUpdate(stockUpdateJson); // Broadcast to all subscribers
            System.out.println(YahooApiRequests++);

        } catch (Exception e) {
            logger.error("Error fetching stocks from Yahoo Finance: {}", e.getMessage());
        }

        return stocks;
    }
}
