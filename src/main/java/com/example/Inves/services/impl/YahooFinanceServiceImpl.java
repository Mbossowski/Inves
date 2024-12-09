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

    @Autowired
    public YahooFinanceServiceImpl(ObjectMapper objectMapper, WebSocketController webSocketController) {
        this.objectMapper = objectMapper;
        this.webSocketController = webSocketController;
    }




    public List<Stock> allStocks(String page) {
        String baseUrl = "https://yahoo-finance15.p.rapidapi.com/api/v2/markets/tickers?page=";
        String url = baseUrl + page + "&type=STOCKS";

        List<Stock> stocks = new ArrayList<>();

        try {
            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("x-rapidapi-key", "e4635e3ab8msh864a7a3425fbdbap1eaa77jsn65a132f3f579")
                    .header("x-rapidapi-host", "yahoo-finance15.p.rapidapi.com")
                    .GET()
                    .build();

            // Send request and get response
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Parse response into DTO list
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode dataNode = rootNode.path("body");

            if (dataNode.isArray()) {

                for (JsonNode stockNode : dataNode) {
                    StockDTO stockDTO = objectMapper.treeToValue(stockNode, StockDTO.class);

                    Stock stock = new Stock();
                    stock.setSymbol(stockDTO.getSymbol());
                    stock.setCompanyName(stockDTO.getName());
                    stock.setMarket("NASDAQ");

                    // Remove the "$" and parse as BigDecimal
                    String priceWithSymbol = stockNode.path("lastsale").asText();
                    if (priceWithSymbol.startsWith("$")) {
                        String price = priceWithSymbol.substring(1);
                        stock.setPrice(new BigDecimal(price));
                    }

                    stock.setValueChange(new BigDecimal(stockDTO.getNetchange()));

                    // Remove the "%" and parse as BigDecimal
                    String pctChangeWithSymbol = stockNode.path("pctchange").asText();
                    if (pctChangeWithSymbol.endsWith("%")) {
                        String pctChange = pctChangeWithSymbol.substring(0, pctChangeWithSymbol.length() - 1);
                        stock.setPercentageChange(new BigDecimal(pctChange));
                    }


                    // Handle marketCap by removing commas and converting to BigDecimal
                    String marketCapWithCommas = stockNode.path("marketCap").asText();
                    String marketCapWithoutCommas = marketCapWithCommas.replace(",", "");
                    stock.setMarketCap(new BigDecimal(marketCapWithoutCommas));

                    stocks.add(stock);
                }
            }

            // Send the updated stock list to clients via WebSocket
            String stockUpdateJson = objectMapper.writeValueAsString(stocks); // Convert stock list to JSON
            webSocketController.sendStockUpdate(stockUpdateJson); // Broadcast to all subscribers

        } catch (Exception e) {
            logger.error("Error fetching stocks from Yahoo Finance: {}", e.getMessage());
        }

        return stocks;
    }
}
