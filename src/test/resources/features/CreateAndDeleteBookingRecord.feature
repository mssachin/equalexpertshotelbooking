Feature: As a User of the Booking System
I should be able to create and delete a booking record
So that I can confirm that my hotel is booked or unbooked

Scenario Outline: Create a booking record and validate
Given I have launched the URL for the booking system
When I enter my "<FirstName>"
And I enter "<LastName>"
And I enter a "<Total Price>"
And I indicate if "<Deposit Paid>"
And I enter "<Check In>" date
And I enter a "<Check Out>" date
And I save the record
Then I check the record count has increased
And the details I entered match with that in the application


Examples:
|FirstName |LastName    |Total Price|Deposit Paid|Check In      |Check Out  |
|Sam       |Higgins     |150.22     | true       |2018-04-30    |2018-05-01 |
|Chetan    |Bhagath     |123.44     | false      |2018-05-01    |2018-05-04 |


Scenario: Delete an existing booking record
Given I have launched the URL for the booking system
When I delete a random existing booking record
Then I check the count of records is reduced

