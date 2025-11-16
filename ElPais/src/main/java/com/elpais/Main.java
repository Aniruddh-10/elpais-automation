package com.elpais;

import org.openqa.selenium.WebDriver;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = EdgeDriverManager.getDriver();
        OpinionScraper scraper = new OpinionScraper(driver);
        Translator translator = new Translator();
        HeaderAnalyzer analyzer = new HeaderAnalyzer();

        List<Article> articles = scraper.scrapeArticles();

        System.out.println("\n Original Articles:");
        for (Article article : articles) {
            System.out.println("\nTitle: " + article.getTitle());
            //Below Print statement checks if Article content length is more than 1000 then it will print up to 1000 characters and ends with ... else prints entire content and ends with ...
            System.out.println("\nContent: " + article.getContent().substring(0, Math.min(1000, article.getContent().length())) + "...");
        }

        List<String> translatedHeaders = new ArrayList<>();
        System.out.println("\n Translated Titles:");
        for (Article article : articles) {
            String translated = translator.translate(article.getTitle());
            article.setTranslatedTitle(translated);
            translatedHeaders.add(translated);
            System.out.println(" " + translated);
        }

        analyzer.analyze(translatedHeaders);

        //Teardown: Close the browser after all the automation is done
        driver.quit();
        System.out.println("Browser closed.");
    }
}
