package com.elpais;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverManager {
    public static WebDriver getDriver() {
        System.setProperty("webdriver.edge.driver", "D:\\Drivers\\edgeDriver-win64\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--lang=es");
        
        WebDriver driver = new EdgeDriver(options);
        driver.manage().window().maximize(); 
        return driver;

    }
}
