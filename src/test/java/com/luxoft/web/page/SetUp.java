package com.luxoft.web.page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SetUp {

    public static WebDriver driver;
    public static WebDriverWait waitVar;


    public SetUp() {
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
//        options.setHeadless(true);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        waitVar = new WebDriverWait(driver,  5);

          driver.navigate().to("https://rozetka.com.ua/");


    }


    @AfterEach
    public static void goStartPage() {
        driver.navigate().to("https://rozetka.com.ua/");

    }



}  ///

