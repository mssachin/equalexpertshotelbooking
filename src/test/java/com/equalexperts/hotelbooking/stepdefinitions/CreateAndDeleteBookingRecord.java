package com.equalexperts.hotelbooking.stepdefinitions;

        import com.equalexperts.hotelbooking.pageobjects.HotelBookingForm;
        import com.equalexperts.hotelbooking.utilities.AssertionUtilities;
        import cucumber.api.java.en.And;
        import cucumber.api.java.en.Then;
        import cucumber.api.java.en.When;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;

        import java.util.ArrayList;
        import java.util.List;

public class CreateAndDeleteBookingRecord {


    private WebDriver driver;
    private TestBase testBase;
    private HotelBookingForm hotelBookingForm;
    private int initialRecordCount;
    private int recordCountAfterCreation;
    private int recordCountAfterDeletion;
    private List<String> allBookingRecordRowIdsBeforeCreation;
    private List<String> allBookingRecordRowIdsAfterCreation;
    private List<String> bookingInformationFromFeatureFile = new ArrayList<>();

    public CreateAndDeleteBookingRecord(TestBase testBase) {
        this.testBase = testBase;
        this.driver = testBase.getDriver();
        hotelBookingForm = new HotelBookingForm(driver);
        initialRecordCount = hotelBookingForm.getNumberOfCurrentBookingRecords();
        allBookingRecordRowIdsBeforeCreation = hotelBookingForm.getRowIdsOfAllExistingBookingRecords();

    }

    @When("^I enter my \"([^\"]*)\"$")
    public void i_enter_my(String firstName) throws Throwable {
        hotelBookingForm.enterFirstName(firstName);
        bookingInformationFromFeatureFile.add(firstName);
    }

    @And("^I enter \"([^\"]*)\"$")
    public void i_enter(String lastName) throws Throwable {
        hotelBookingForm.enterLastName(lastName);
        bookingInformationFromFeatureFile.add(lastName);

    }

    @And("^I enter a \"([^\"]*)\"$")
    public void i_enter_a(String totalPrice) throws Throwable {
        hotelBookingForm.enterTotalPrice(totalPrice);
        bookingInformationFromFeatureFile.add(totalPrice);
    }

    @And("^I indicate if \"([^\"]*)\"$")
    public void i_indicate_if(String depositPaidIndicator) throws Throwable {
        hotelBookingForm.indicateDepositPaid(depositPaidIndicator);
        bookingInformationFromFeatureFile.add(depositPaidIndicator);

    }

    @And("^I enter \"([^\"]*)\" date$")
    public void i_enter_checkIn_date(String date) throws Throwable {
        hotelBookingForm.enterCheckInDate(date);
        bookingInformationFromFeatureFile.add(date);
    }

    @And("^I enter a \"([^\"]*)\" date$")
    public void i_enter_checkOut_date(String date) throws Throwable {
        hotelBookingForm.enterCheckOutDate(date);
        bookingInformationFromFeatureFile.add(date);

    }

    @And("^I save the record$")
    public void i_save_the_record() throws Throwable {
        hotelBookingForm.saveNewlyCreatedRecord();
        driver.navigate().refresh();
    }

    @Then("^I check the record count has increased$")
    public void i_check_the_record_count_has_increased() throws Throwable {
        hotelBookingForm.explicitWaitForSaveButton();
        recordCountAfterCreation =hotelBookingForm.getNumberOfCurrentBookingRecords();
        AssertionUtilities.assertTrue(recordCountAfterCreation > initialRecordCount, "New Record was not created");

    }

    @And("^the details I entered match with that in the application$")
    public void the_details_I_entered_match_with_that_in_the_application() throws Throwable {
        allBookingRecordRowIdsAfterCreation = hotelBookingForm.getRowIdsOfAllExistingBookingRecords();
        AssertionUtilities.assertTrue(allBookingRecordRowIdsAfterCreation.size() >allBookingRecordRowIdsBeforeCreation.size(), "New Record was not created");
        List<String> newlyCreatedRecordsRowIds = new ArrayList<>();
        for (String rowId:allBookingRecordRowIdsAfterCreation) {
            if(!allBookingRecordRowIdsBeforeCreation.contains(rowId)){
                newlyCreatedRecordsRowIds.add(rowId);
            }
        }
        List<WebElement> newlyCreatedRowElements = hotelBookingForm.getRowElementsOfNewlyCreatedRecords(newlyCreatedRecordsRowIds);
        List<String> rowInformation;
        int numberOfMatchingRecords=0;
        for(WebElement rowElement: newlyCreatedRowElements){
            rowInformation = hotelBookingForm.extractRecordInformationFromWebElement(rowElement);
            if(rowInformation.containsAll(bookingInformationFromFeatureFile)){
                numberOfMatchingRecords++;
            }
        }
        AssertionUtilities.assertTrue(numberOfMatchingRecords == 1, "New record was not created successfully with following details "+ " First Name: "+bookingInformationFromFeatureFile.get(0)+ " Last Name: "+bookingInformationFromFeatureFile.get(1)+ " Total Price: "+bookingInformationFromFeatureFile.get(2)+ " Deposit Paid: "+bookingInformationFromFeatureFile.get(3)+ " CheckIn Date: "+bookingInformationFromFeatureFile.get(4)+ " CheckOut Date: "+bookingInformationFromFeatureFile.get(5));
    }



    @When("^I delete a random existing booking record$")
    public void i_delete_a_random_existing_booking_record() throws Throwable {
        hotelBookingForm.deleteARandomBookingRecord();
        driver.navigate().refresh();
    }

    @Then("^I check the count of records is reduced$")
    public void i_check_the_count_of_records_is_reduced() throws Throwable {
        recordCountAfterDeletion = hotelBookingForm.getNumberOfCurrentBookingRecords();
        AssertionUtilities.assertTrue(recordCountAfterDeletion < initialRecordCount, "Record was not deleted");
    }


}
