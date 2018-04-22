package com.equalexperts.hotelbooking.stepdefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;
    private boolean isInitialized;

    @Before
    public void  initializeEnvironment(){
        if(!isInitialized) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            driver = new ChromeDriver(chromeOptions);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            isInitialized = true;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
    @After
    public void tearDownEnvironment(){
        driver.quit();
    }
}