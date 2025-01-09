package com.example.Inves.services.impl;

import com.example.Inves.models.MarketNews;
import com.example.Inves.services.MarketNewsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:24
 */
@Transactional
@Service
public class MarketNewsServiceImpl implements MarketNewsService {

    @Value("${nasdaq.key}")
    private String apiKey;
    private final String API_URL = "https://www.nasdaq.com/api/news/topic/articlebysymbol?q=" ;
    private static final Logger LOGGER = Logger.getLogger(StockTickerService.class.getName());
    private final HttpClient httpClient;


    public MarketNewsServiceImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    public List<MarketNews> getStockNews(String symbol, int offset, int limit) {
        List<MarketNews> news = new ArrayList<>();

        try {
            LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
            HttpGet request = new HttpGet(API_URL + symbol +  "&offset=" + offset + "&limit=" + limit + "&fallback=true&api_key=" + apiKey);
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            request.addHeader("Accept", "application/json");


            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                LOGGER.severe("Failed to fetch data. HTTP Status: " + statusCode);
                return news;
            }

            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse).path("data").path("rows");

            for (JsonNode node : root) {

                String title = node.path("title").asText("");
                String publishedDate =  node.path("created").asText("");
                String topic = node.path("primarytopic").asText("");
                topic = topic.split("\\|")[0];
                String source = node.path("publisher").asText("");
                String site = "https://www.nasdaq.com" + node.path("url").asText("");


                MarketNews newNews = new MarketNews(title,publishedDate,topic,source,site);
                news.add(newNews);
            }


            LOGGER.info("Fetched " + news.size() + " news. " + currentDateTime );
        } catch (Exception e) {
            LOGGER.severe("Error fetching tickers: " + e.getMessage());
        }

        return news;
    }
}
