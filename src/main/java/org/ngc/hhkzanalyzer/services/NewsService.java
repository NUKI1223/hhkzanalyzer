package org.ngc.hhkzanalyzer.services;

import org.ngc.hhkzanalyzer.model.News;
import org.ngc.hhkzanalyzer.model.NewsApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NewsService {
    private static final String API_KEY = "7c41726b2d864118996e67a3ad2f5174";
    private static final String API_URL = "https://newsapi.org/v2/everything?q=IT&language=en&apiKey=" + API_KEY;

    public final RestTemplate restTemplate;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<News> getNews() {
        NewsApiResponse response = restTemplate.getForObject(API_URL, NewsApiResponse.class);
        return response != null ? Arrays.asList(response.getArticles()) : List.of();
    }
}
