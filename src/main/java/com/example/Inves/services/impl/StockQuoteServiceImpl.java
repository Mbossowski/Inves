package com.example.Inves.services.impl;

import com.example.Inves.models.Stock;
import com.example.Inves.models.StockQuote;
import com.example.Inves.persistence.repositories.StockDAORepository;
import com.example.Inves.persistence.repositories.StockQuoteDAORepository;
import com.example.Inves.services.StockQuoteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 02/01/2025 - 20:13
 */
@Transactional
@Service
public class StockQuoteServiceImpl implements StockQuoteService {

    @Value("${nasdaq.key}")
    private String apiKey;
    private final String API_URL = "https://query2.finance.yahoo.com/v8/finance/chart/";
    private static final Logger LOGGER = Logger.getLogger(StockQuoteService.class.getName());
    private final HttpClient httpClient;
    private StockQuoteDAORepository StockQuoteRepository;


    @Autowired
    public StockQuoteServiceImpl(StockQuoteDAORepository StockQuoteRepository) {
        this.StockQuoteRepository = StockQuoteRepository;
        this.httpClient = HttpClients.createDefault();

    }

    public List<StockQuote> getStockQuotesBySymbolForLastPeriod(String symbol, Long period) {


        ZoneId zoneId = ZoneId.of("Europe/Warsaw");

        // Dzisiejszy dzień o 15:30
            LocalDateTime startTime = LocalDateTime.now()
                    .withHour(15)
                    .withMinute(30)
                    .withSecond(0)
                    .withNano(0)
                    .minusDays(period);

        // Dzisiejszy dzień o 22:00
        LocalDateTime endTime = LocalDateTime.now()
                .withHour(22)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        if(LocalDateTime.now(zoneId).getHour() < 15 || (LocalDateTime.now(zoneId).getHour() == 15&& LocalDateTime.now(zoneId).getMinute() < 30)) {
            startTime = startTime.minusDays(1);
            endTime = endTime.minusDays(1);
        }

        // Konwersja do czasu epoch w sekundach
        long epochStart = ZonedDateTime.of(startTime, zoneId).toEpochSecond();
        long epochEnd = ZonedDateTime.of(endTime, zoneId).toEpochSecond();


        List<StockQuote> quotes = new ArrayList<>();
        try {
            LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
            HttpGet request = new HttpGet(API_URL + symbol + "?period1=" + epochStart + "&period2=" + epochEnd + "&interval=" + period+1 + "m&includePrePost=true&events=div%7Csplit%7Cearn&&lang=en-US&region=US");
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            request.addHeader("Accept", "application/json");


            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                LOGGER.severe("Failed to fetch data. HTTP Status: " + statusCode + " , " +  API_URL + symbol + "?period1=" + epochStart + "&period2=" + epochEnd + "&interval=1m&lang=en-US&region=US");
                return quotes;
            }

            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(request);
            JsonNode root = mapper.readTree(jsonResponse).path("chart").path("result").get(0);



            List<Date> DateList = convertToDateList(root.path("timestamp"));


            JsonNode quoteNode = root.path("indicators").path("quote").get(0);
            // Odczytaj listy wartości
            List<Double> closeList = convertToDoubleList(quoteNode.path("close"));
            List<Double> openList = convertToDoubleList(quoteNode.path("open"));

            List<Double> lowList = convertToDoubleList(quoteNode.path("low"));
            List<Double> highList = convertToDoubleList(quoteNode.path("high"));


            List<Long> volumeList = convertToLongList(quoteNode.path("volume"));


            int size = closeList.size(); // Przyjmujemy, że wszystkie listy mają taką samą długość

            for (int i = 0; i < size; i++) {
                if(openList.get(i) == 0 ||  closeList.get(i) == 0 ||  lowList.get(i) == 0 ||  highList.get(i) == 0)
                {
                    continue;
                }
                StockQuote quote = new StockQuote(
                        symbol,
                        DateList.get(i),
                        openList.get(i),
                        closeList.get(i),
                        lowList.get(i),
                        highList.get(i),
                        volumeList.get(i)
                );
                quotes.add(quote);
            }

            LOGGER.info("Fetched " + quotes.size() + " quotes. " + currentDateTime );
        } catch (Exception e) {
            LOGGER.severe("Error fetching quotes: " + e.getMessage());
        }
        return quotes;
    }

    // Pomocnicza metoda do konwersji JsonNode na Listę Double
    private static List<Double> convertToDoubleList(JsonNode jsonArray) {
        List<Double> list = new ArrayList<>();
        if (jsonArray.isArray()) { // Sprawdza, czy jsonArray to lista
            for (JsonNode node : jsonArray) {
                double value = node.asDouble();
                // Zaokrąglenie do dwóch miejsc po przecinku
                BigDecimal roundedValue = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
                list.add(roundedValue.doubleValue()); // Dodaje zaokrągloną wartość do listy
            }
        }
        return list;
    }

    // Pomocnicza metoda do konwersji JsonNode na Listę Long
    private static List<Long> convertToLongList(JsonNode jsonArray) {
        List<Long> list = new ArrayList<>();
        if (jsonArray.isArray()) { // Sprawdza, czy jsonArray to lista
            for (JsonNode node : jsonArray) {

                list.add(node.asLong()); // Konwertuje elementy do Double
            }
        }
        return list;
    }

    // Pomocnicza metoda do konwersji JsonNode na Listę Date
    public static List<Date> convertToDateList(JsonNode jsonArray) {

        List<Date> dateList = new ArrayList<>();
        if (jsonArray.isArray()) { // Sprawdza, czy jsonArray to lista
            for (JsonNode node : jsonArray) {
                Date date = new Date(node.asLong()*1000);

                // Sformatowanie daty
                dateList.add(date);
            }
        }
        return dateList;
    }
}



