package org.ngc.hhkzanalyzer.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Vakancy {
    private String allText;
    private String vakancyName;
    //TODO Сделать библеотеку всех технологий с разных вакансий и уже через эту библеотеку выполнять поиск

    @Override
    public String toString() {
        return "Vakancy{" +
                "vakancyName='" + vakancyName + '\'' +
                ", allText='" + allText + '\'' +
                '}';
    }
}
