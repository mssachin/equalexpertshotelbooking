package com.equalexperts.hotelbooking.pageobjects;

import com.equalexperts.hotelbooking.utilities.WebUtilityFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HotelBookingForm {

    private WebDriver driver;
    private static final By allBookingsContainerLocator = By.id("bookings");
    private static final By singleBookingRowLocator = By.className("row");
    private static final By firstNameTextBoxLocator = By.id("firstname");
    private static final By lastNameTextBoxLocator = By.id("lastname");
    private static final By totalPriceInputLocator = By.id("totalprice");
    private static final By depositPaidDropdownLocator = By.id("depositpaid");
    private static final By checkInDateInputLocator = By.id("checkin");
    private static final By checkOutDateInputLocator = By.id("checkout");
    private static final By saveButtonLocator = By.cssSelector("input[value=' Save ']");
    private static final By deleteButtonLocator = By.cssSelector("input[value='Delete']");
    private static final By divsInsideRowLocator = By.tagName("div");





    public HotelBookingForm(WebDriver driver) {
        this.driver = driver;
    }

    public int getNumberOfCurrentBookingRecords(){
        WebElement allBookingsContainerElement = WebUtilityFunctions.returnWebElement(driver, allBookingsContainerLocator);
        List<WebElement> allBookingRowsElements = WebUtilityFunctions.returnWebElementsInsideAnElement(allBookingsContainerElement, singleBookingRowLocator);
        return allBookingRowsElements.size();
    }

    public void enterFirstName(String firstName){
        WebUtilityFunctions.enterTextInTextBox(driver, firstNameTextBoxLocator, firstName);
    }

    public void enterLastName(String lastName){
        WebUtilityFunctions.enterTextInTextBox(driver, lastNameTextBoxLocator, lastName);
    }

    public void enterTotalPrice(String totalPrice){
        WebUtilityFunctions.enterTextInTextBox(driver, totalPriceInputLocator, totalPrice);
    }

    public void indicateDepositPaid(String depositPaidIndicator){
        WebUtilityFunctions.selectValueFromADropdownByVisibleText(driver, depositPaidDropdownLocator, depositPaidIndicator);
    }

    public void enterCheckInDate(String checkInDate){
        WebUtilityFunctions.enterTextInTextBox(driver, checkInDateInputLocator, checkInDate);
    }

    public void enterCheckOutDate(String checkOutDate){
        WebUtilityFunctions.enterTextInTextBox(driver, checkOutDateInputLocator, checkOutDate);
    }

    public void saveNewlyCreatedRecord(){
        WebUtilityFunctions.linkOrButtonClick(driver, saveButtonLocator);
    }

    public List<String> getRowIdsOfAllExistingBookingRecords(){
        WebElement allBookingsContainerElement = WebUtilityFunctions.returnWebElement(driver, allBookingsContainerLocator);
        List<WebElement> allBookingRowsElements = WebUtilityFunctions.returnWebElementsInsideAnElement(allBookingsContainerElement, singleBookingRowLocator);
        List<String> allBookingRowsRowIds = new ArrayList<>();

        for(int i=1; i<allBookingRowsElements.size(); i++){
            String idAttribute = allBookingRowsElements.get(i).getAttribute("id");
            allBookingRowsRowIds.add(idAttribute);
        }

        return allBookingRowsRowIds;

    }

    public List<WebElement> getRowElementsOfNewlyCreatedRecords(List<String> rowIdsOfNewlyCreatedRecords){
        List<WebElement> newlyCreatedRowElements = new ArrayList<>();
        for(String rowIdOfEachRecord :rowIdsOfNewlyCreatedRecords){
            WebElement rowElement = WebUtilityFunctions.returnWebElementBasedOnId(driver, rowIdOfEachRecord);
            newlyCreatedRowElements.add(rowElement);
        }
        return newlyCreatedRowElements;
    }

    public List<String> extractRecordInformationFromWebElement(WebElement recordElement){
        List<WebElement> allDivsInsideRowElement = WebUtilityFunctions.returnWebElementsInsideAnElement(recordElement, divsInsideRowLocator);
        List<String> recordInformation = new ArrayList<>();
        for(WebElement eachDivElement: allDivsInsideRowElement){
            recordInformation.add(eachDivElement.getText());
        }
        return  recordInformation;
    }

    public WebElement returnARandomBookingRow(){
        WebElement allBookingsContainerElement = WebUtilityFunctions.returnWebElement(driver, allBookingsContainerLocator);
        List<WebElement> allBookingRowsElements = WebUtilityFunctions.returnWebElementsInsideAnElement(allBookingsContainerElement, singleBookingRowLocator);
        Random random = new Random();
        List<WebElement> allBookingRowElementsExcludingHeader = allBookingRowsElements.subList(1, allBookingRowsElements.size());
        return allBookingRowElementsExcludingHeader.get(random.nextInt(allBookingRowElementsExcludingHeader.size()));
    }


    public void deleteARandomBookingRecord(){
        WebElement randomBookingRecordElement = returnARandomBookingRow();
        WebElement deleteButtonElement = WebUtilityFunctions.returnWebElementInsideElement(randomBookingRecordElement, deleteButtonLocator);
        WebUtilityFunctions.clickWebElement(deleteButtonElement);
    }

    public void explicitWaitForSaveButton(){
        WebUtilityFunctions.explicitWaitForAnElement(driver, saveButtonLocator);
    }







}

