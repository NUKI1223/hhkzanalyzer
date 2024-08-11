package org.ngc.hhkzanalyzer.controller;

import org.ngc.hhkzanalyzer.services.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
    private final NewsService newsService;
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    @GetMapping("/")
    public String mainpage(Model model) {
        model.addAttribute("news", newsService.getNews());
        return "mainpage";
    }
}
