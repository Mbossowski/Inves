package com.example.Inves.services.impl;
import com.example.Inves.models.Stock;
import com.example.Inves.models.StockQuote;
import com.example.Inves.persistence.repositories.StockDAORepository;
import com.example.Inves.services.StockQuoteService;
import com.example.Inves.services.StockService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;



import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:25
 */
@Transactional
@Service
public class StockServiceImpl implements StockService {

    private StockDAORepository StockRepository;

    private final String API_URL = "https://query2.finance.yahoo.com/v8/finance/chart/";
    private static final Logger LOGGER = Logger.getLogger(StockQuoteService.class.getName());
    private final HttpClient httpClient;

    @Autowired
    public StockServiceImpl(StockDAORepository StockRepository) {
        this.StockRepository = StockRepository;
        this.httpClient = HttpClients.createDefault();
    }


    @Override
    public Page<Stock> allStocks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return StockRepository.findAll(pageable);
    }

    @Override
    public Page<Stock> getStocks(String query, Pageable pageable) {
        if (query == null || query.isEmpty()) {
            return StockRepository.findAll(pageable);
        }
        return StockRepository.findBySymbolContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(query, query, pageable);
    }

    @Override
    public Optional<Stock> getStock(String symbol)
    {
         return StockRepository.findBySymbol(symbol);
    }

    public Stock getStockDetails(String symbol)
    {

        ZoneId zoneId = ZoneId.of("Europe/Warsaw");

        // Dzisiejszy dzień o 15:30
        LocalDateTime someTime = LocalDateTime.now()
                .withHour(15)
                .withMinute(30)
                .withSecond(0)
                .withNano(0);





        // Konwersja do czasu epoch w sekundach
        long epoch = ZonedDateTime.of(someTime, zoneId).toEpochSecond();



        Stock stock = new Stock();
        try {
            LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
            HttpGet request = new HttpGet(API_URL + symbol + "?period1=" + epoch + "&period2=" + epoch + "&interval=1m&includePrePost=true&events=div%7Csplit%7Cearn&&lang=en-US&region=US");
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            request.addHeader("Accept", "application/json");


            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                LOGGER.severe("Failed to fetch data. HTTP Status: " + statusCode + " , " +  API_URL + symbol + "?period1=" + epoch + "&period2=" + epoch + "&interval=1m&lang=en-US&region=US");
                return stock;
            }

            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(request);

            JsonNode metaNode = mapper.readTree(jsonResponse).path("chart").path("result").get(0).path("meta");
            Double fiftyTwoWeekHigh = metaNode.path("fiftyTwoWeekHigh").asDouble();
            Double fiftyTwoWeekLow = metaNode.path("fiftyTwoWeekLow").asDouble();
            Double regularMarketDayHigh = metaNode.path("regularMarketDayHigh").asDouble();
            Double regularMarketDayLow = metaNode.path("regularMarketDayLow").asDouble();
            Integer volume =  metaNode.path("regularMarketVolume").asInt();
            Double previousClose = metaNode.path("previousClose").asDouble();


            StockRepository.updateStockDetails(symbol, fiftyTwoWeekHigh, fiftyTwoWeekLow,regularMarketDayHigh,regularMarketDayLow,volume,previousClose);


            stock.setFiftyTwoWeekHigh(fiftyTwoWeekHigh);
            stock.setFiftyTwoWeekLow(fiftyTwoWeekLow);
            stock.setRegularMarketDayHigh(regularMarketDayHigh);
            stock.setRegularMarketDayLow(regularMarketDayLow);
            stock.setVolume(volume);
            stock.setPreviousClose(previousClose);



            LOGGER.info("Fetched stock details quotes. " + currentDateTime );
        } catch (Exception e) {
            LOGGER.severe("Error fetching quotes: " + e.getMessage());
        }
    return stock;

    }

    
}
