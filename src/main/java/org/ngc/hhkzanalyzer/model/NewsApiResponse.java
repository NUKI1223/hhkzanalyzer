package org.ngc.hhkzanalyzer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsApiResponse {
    private News[] articles;
}
