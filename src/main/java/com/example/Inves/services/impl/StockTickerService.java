package com.example.Inves.services.impl;

import com.example.Inves.models.Stock;
import com.example.Inves.models.StockPrice;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 27/12/2024 - 18:49
 */
@Service
public class StockTickerService {
    @Value("${nasdaq.key}")
    private String apiKey;
    private final String API_URL = "https://api.nasdaq.com/api/screener/stocks?download=true&api_key=" + apiKey;
    private static final Logger LOGGER = Logger.getLogger(StockTickerService.class.getName());
    private final HttpClient httpClient;


    public StockTickerService() {
        this.httpClient = HttpClients.createDefault();
    }

    public List<Stock> getStockTickers(String exchange, Long minMarketCap, Long maxMarketCap, String sector) {
        List<Stock> tickers = new ArrayList<>();

        try {
            LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
            HttpGet request = new HttpGet(API_URL + "&exchange=" + exchange);
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            request.addHeader("Accept", "application/json");


            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                LOGGER.severe("Failed to fetch data. HTTP Status: " + statusCode);
                return tickers;
            }

            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse).path("data").path("rows");

            for (JsonNode node : root) {

                String symbol = node.path("symbol").asText("");
                String companyName = node.path("name").asText("");
                Double lastSale = 0.0;
                try {
                    lastSale = Double.parseDouble(node.path("lastsale").asText("0.0").replaceAll("[$]", ""));
                } catch (NumberFormatException e) {
                    // Obsłuż błąd parsowania liczby
                }

                Double netChange = node.path("netchange").asDouble(0.0);
                Double pctChange = 0.0;
                try {
                    pctChange = Double.parseDouble(node.path("pctchange").asText("0.0").replaceAll("[%]", ""));
                } catch (NumberFormatException e) {
                    // Obsłuż błąd parsowania liczby
                }

                Integer volume = node.path("volume").asInt(0);
                Double marketCap = 0.0;
                try {
                    marketCap = Double.parseDouble(node.path("marketCap").asText("0.0").replaceAll("[,]", ""));
                } catch (NumberFormatException e) {
                    // Obsłuż błąd parsowania liczby
                }

                String market = exchange; // zakładam, że exchange zawsze istnieje
                String country = node.path("country").asText("");
                String industry = node.path("industry").asText("");
                String ssector = node.path("sector").asText("");



                Stock newStock = new Stock(symbol,companyName,lastSale,netChange,pctChange,volume,marketCap,market,country,industry,ssector);

                // Apply filters
                if ((minMarketCap == null || marketCap >= minMarketCap) &&
                        (maxMarketCap == null || marketCap <= maxMarketCap) &&
                        (sector == null || sector.equalsIgnoreCase(ssector))) {

                    tickers.add(newStock);  // Add symbol and last sale price
                }
            }

            LOGGER.info("Fetched " + tickers.size() + " tickers. " + currentDateTime );
        } catch (Exception e) {
            LOGGER.severe("Error fetching tickers: " + e.getMessage());
        }

        return tickers;
    }
}