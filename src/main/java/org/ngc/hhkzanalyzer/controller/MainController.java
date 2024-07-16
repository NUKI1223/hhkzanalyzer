package org.ngc.hhkzanalyzer.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.ngc.hhkzanalyzer.services.AnalyzeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {
    private final AnalyzeService analyzeService;

    public MainController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @GetMapping("/tech")
    public String Main(Model model) {
        String url = "https://hh.kz/search/vacancy?text=Python&from=suggest_post&salary=&ored_clusters=true&area=159&hhtmFrom=vacancy_search_list&hhtmFromLabel=vacancy_search_line";

        try {
            HttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD)
                            .build())
                    .build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String html = new String(entity.getContent().readAllBytes());
                Map<String, Integer> technologyCounts = analyzeService.parseHtml(html);
                model.addAttribute("technologyCounts", technologyCounts);
                return "technologies";

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No";
    }
}
