package com.elpais;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

public class OpinionScraper {
    private WebDriver driver;

    public OpinionScraper(WebDriver driver) {
        this.driver = driver;
    }

    public List<Article> scrapeArticles() {
        List<Article> articles = new ArrayList<>();

        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
            driver.get("https://elpais.com/opinion/");
        } catch (TimeoutException e) {
            System.out.println("Opinion page load timed out. Proceeding anyway...");
        }

        acceptCookies();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("article")));
            List<WebElement> articleElements = driver.findElements(By.cssSelector("article"));

            Set<String> articleUrls = new LinkedHashSet<>();
            for (WebElement article : articleElements) {
                try {
                    WebElement link = article.findElement(By.tagName("a"));
                    String url = link.getAttribute("href");
                    if (url != null && !url.trim().isEmpty()) {
                        articleUrls.add(url);
                    }
                } catch (Exception e) {
                    if (articleUrls.size() % 10 == 0) {
                        System.out.println("Some articles had no link. Skipping...");
                    }
                }
                if (articleUrls.size() >= 10) break;
            }

            Set<String> seenTitles = new HashSet<>();
            int index = 0;
            for (String url : articleUrls) {
                driver.navigate().to(url);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("article")));

                String title = "";
                try {
                    WebElement articleElement = driver.findElement(By.tagName("article"));
                    List<WebElement> h2s = articleElement.findElements(By.tagName("h2"));
                    title = !h2s.isEmpty() ? h2s.get(0).getText() : driver.findElement(By.tagName("h1")).getText();
                } catch (Exception e) {
                    System.out.println("Failed to extract title: " + e.getMessage());
                }

                if (seenTitles.contains(title)) {
                    System.out.println("Duplicate article skipped: " + title);
                    continue;
                }
                seenTitles.add(title);

                String content = "";
                try {
                    content = driver.findElement(By.tagName("article")).getText();
                } catch (Exception e) {
                    System.out.println("Failed to extract content: " + e.getMessage());
                }

                String imageUrl = "";
                try {
                    WebElement img = driver.findElement(By.cssSelector("article img"));
                    imageUrl = img.getAttribute("src");
                    downloadImage(imageUrl, "article_" + index + ".jpg");
                } catch (Exception e) {
                    System.out.println("No image found for article " + (index + 1));
                }

                articles.add(new Article(title, content, imageUrl));
                index++;

                if (articles.size() >= 5) break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    private void acceptCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#didomi-notice-agree-button > span")));
            cookieButton.click();
            System.out.println("Cookie consent accepted.");
        } catch (TimeoutException e) {
            System.out.println("No cookie popup appeared.");
        } catch (Exception e) {
            System.out.println("Error while accepting cookies: " + e.getMessage());
        }
    }

    private void downloadImage(String imageUrl, String fileName) {
        try {
            File dir = new File("images");
            if (!dir.exists()) dir.mkdirs();
            FileUtils.copyURLToFile(new URL(imageUrl), new File(dir, fileName));
        } catch (IOException e) {
            System.out.println("Failed to download image: " + imageUrl);
        }
    }
}
