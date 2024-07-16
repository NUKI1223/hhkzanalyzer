package org.ngc.hhkzanalyzer.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ngc.hhkzanalyzer.model.Vakancy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AnalyzeService {
    private static final List<String> TECHNOLOGIES = Arrays.asList(
            // Языки программирования
            "Java", "Python", "JavaScript", "TypeScript", "C#", "C++", "Ruby", "PHP", "Swift",
            "Kotlin", "Go", "Rust", "Scala", "R", "Dart", "Perl", "Objective-C", "Shell",

            // Frontend фреймворки и библиотеки
            "React", "Angular", "Vue.js", "Ember.js", "Svelte", "Backbone.js", "jQuery",

            // Backend фреймворки и библиотеки
            "Spring", "Django", "Flask", "Express.js", "Ruby on Rails", "Laravel", "Symfony",
            "ASP.NET", "Gin", "Koa", "FastAPI", "NestJS",

            // Базы данных
            "MySQL", "PostgreSQL", "MongoDB", "SQLite", "MariaDB", "Oracle DB", "Microsoft SQL Server",
            "Redis", "Cassandra", "CouchDB", "Firebase Realtime Database", "Elasticsearch",

            // Инструменты DevOps
            "Docker", "Kubernetes", "Jenkins", "GitLab CI/CD", "CircleCI", "Travis CI",
            "Ansible", "Puppet", "Chef", "Terraform", "Vagrant", "Nagios", "Prometheus",
            "Grafana", "Splunk",

            // Облачные платформы
            "Amazon Web Services (AWS)", "Microsoft Azure", "Google Cloud Platform (GCP)",
            "IBM Cloud", "Oracle Cloud", "DigitalOcean", "Heroku", "Linode",

            // Системы управления версиями
            "Git", "Subversion (SVN)", "Mercurial", "Perforce",

            // Инструменты для разработки
            "Visual Studio Code", "IntelliJ IDEA", "Eclipse", "PyCharm", "WebStorm",
            "NetBeans", "Sublime Text", "Atom",

            // Тестирование
            "JUnit", "pytest", "Selenium", "Cypress", "Jest", "Mocha",
            "RSpec", "Robot Framework", "Postman", "SoapUI",

            // Управление проектами и Agile
            "JIRA", "Trello", "Asana", "Monday.com", "Basecamp", "Confluence", "Slack",

            // Прочие технологии и инструменты
            "Nginx", "Apache HTTP Server", "HAProxy", "Varnish", "RabbitMQ", "Kafka",
            "ActiveMQ", "Zookeeper", "Logstash", "Kibana", "Consul", "Vault",
            "Istio", "Linkerd"
    );
    public String parseHtml(String html) throws IOException {
        List<Vakancy> posts = new ArrayList<Vakancy>();
        Document document = Jsoup.parse(html);

        Elements name = document.getElementsByAttributeValue("class", "serp-item__title-link-wrapper");
        for (Element element : name) {
            String detailsLink = element.child(0).attr("href");
            Vakancy vakancy = new Vakancy();
            Document postDetails = Jsoup.connect(detailsLink).get();
            vakancy.setAllText(postDetails.getElementsByClass("g-user-content").first().text());
            vakancy.setVakancyName(element.getElementsByAttributeValue("data-qa", "serp-item__title").text());
            posts.add(vakancy);
        }
        List<String> allTechnologies = new ArrayList<>();

        for (Vakancy vakancy : posts){
            String description = vakancy.getAllText();
            List<String> foundTechnologies = extractTechnologies(description);
            allTechnologies.addAll(foundTechnologies);

        }
        Map<String, Integer> technologyCounts = countTechnologyOccurrences(allTechnologies);
        StringBuilder result = new StringBuilder();
        technologyCounts.forEach((tech, count) -> result.append(tech).append(": ").append(count).append("\n"));
        String resultString = result.toString();
        return resultString;

    }
    public static Map<String, Integer> countTechnologyOccurrences(List<String> technologies) {
        Map<String, Integer> technologyCounts = new HashMap<>();
        for (String tech : technologies) {
            technologyCounts.put(tech, technologyCounts.getOrDefault(tech, 0) + 1);
        }
        return technologyCounts;
    }
    public static List<String> extractTechnologies(String text) {
        String lowerCaseText = text.toLowerCase();

        return TECHNOLOGIES.stream()
                .filter(tech -> {
                    String regex = "\\b" + Pattern.quote(tech.toLowerCase()) + "\\b";
                    return Pattern.compile(regex).matcher(lowerCaseText).find();
                })
                .collect(Collectors.toList());
    }
}