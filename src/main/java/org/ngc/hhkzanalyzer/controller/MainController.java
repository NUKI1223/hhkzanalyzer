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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final AnalyzeService analyzeService;
    private static final List<String> LANGUAGES = Arrays.asList("-", "Java", "Python", "JavaScript", "C#", "C++", "Ruby", "PHP", "Swift", "Kotlin", "Go", "Rust", "Scala", "R");
    private static final List<String> POSITIONS = Arrays.asList("-", "Backend", "Frontend", "FullStack", "DevOps", "DataScientist", "QA", "ProjectManager", "ProductOwner");


    public MainController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @GetMapping("/tech")
    public String tech(Model model) {
        model.addAttribute("languages", LANGUAGES);
        model.addAttribute("positions", POSITIONS);
        return "technologies";
    }

    @GetMapping("/techCount")
    public String techCount(Model model, @RequestParam("language") String language, @RequestParam("position") String position) {

        String url = "";
        if (language=="-") {
            url = "https://hh.kz/search/vacancy?text="+ position+ "&from=suggest_post&salary=&ored_clusters=true&area=159&hhtmFrom=vacancy_search_list&hhtmFromLabel=vacancy_search_line";
        } else if (position=="-") {
            url = "https://hh.kz/search/vacancy?text="+ language+ "&from=suggest_post&salary=&ored_clusters=true&area=159&hhtmFrom=vacancy_search_list&hhtmFromLabel=vacancy_search_line";
        }
        else {
            url = "https://hh.kz/search/vacancy?text="+ language+"+"+position+"&from=suggest_post&salary=&ored_clusters=true&area=159&hhtmFrom=vacancy_search_list&hhtmFromLabel=vacancy_search_line";

        }
        Map<String, Integer> technologyCounts = analyzeService.parseHtml(url);
        model.addAttribute("technologyCounts", technologyCounts);
        return "technologiesCount";
    }
}
