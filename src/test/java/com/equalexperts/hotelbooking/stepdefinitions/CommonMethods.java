package com.equalexperts.hotelbooking.stepdefinitions;

import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;

public class CommonMethods {


    private WebDriver driver;
    private TestBase testBase;

    public CommonMethods(TestBase testBase) {
        this.testBase = testBase;
        driver = testBase.getDriver();
    }

    @Given("^I have launched the URL for the booking system$")
    public void i_have_launched_the_URL_for_the_booking_system() throws Throwable {
     driver.get("http://hotel-test.equalexperts.io");
    }

}
