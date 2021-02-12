Feature: Saving to Database

  Acceptance Criteria:
  1. User can save all created data to database.
  2. User can retrieve all data from database.

  Scenario: Creating Persons of Contact and saving to database
    Given Person Of Contact "John Smith" is created
    And Person Of Contact "Brad Pit" is created
    When User clicks on SAVE
    Then 2 Persons Of Contact are saved in database

  Scenario: Creating Addresses and saving to database
    Given Address "First Address" is created
    And Address "Second Address" is created
    When User clicks on SAVE
    Then 2 Addresses are saved in database

  Scenario: Creating Prospects and saving to database
    Given Prospect "Testing" is created
    And Prospect "Building" is created
    When User clicks on SAVE
    Then 2 Prospects are saved in database

    Scenario: Clicking on SAVE multiple times and verifying if no duplicates are saved
      Given Prospect "Testing" is created
      And Person Of Contact "John Smith" is created
      And Address "First Address" is created
      When User clicks on SAVE
      And User clicks on SAVE
      Then 1 Person Of Contact is saved in database
      And 1 Address is saved in database
      And 1 Address is saved in database

  Scenario: Updating Prospects data and saving to database
    Given Prospect "Testing" is created
    When User clicks on SAVE
    Then 1 Prospect is saved in database
    When Setting Tax ID of Prospect "Testing" to "123456789"
    And Setting Principal Activity of Prospect "Testing" to "Testing activity"
    And User clicks on SAVE
    Then As per database Tax ID of Prospect "Testing" is "123456789"
    And As per database Principal Activity of Prospect "Testing" is "Testing activity"
    When Setting Tax ID of Prospect "Testing" to "123456788"
    And Setting Principal Activity of Prospect "Testing" to "Building activity"

  Scenario: Assigning Person of Contact to Prospect and saving to database
    Given Person Of Contact "John Smith" is created
    And Prospect "Building" is created
    When Adding Person Of Contact "John Smith" to Prospect "Building"
    And User clicks on SAVE
    Then 1 Person Of Contact is saved in database
    And 1 Prospect is saved in database
    And As per database "John Smith" is a Person of Contact for Prospect "Building"

  Scenario: Assigning Address to Prospect and saving to database
    Given Address "First Address" is created
    And Prospect "Building" is created
    When Adding Address "First Address" to Prospect "Building"
    And User clicks on SAVE
    Then 1 Address is saved in database
    And 1 Prospect is saved in database
    And As per database "First Address" is Address for Prospect "Building"

  Scenario: Removing Person of Contact from Prospect and saving to database
    Given Person Of Contact "John Smith" is created
    And Prospect "Building" is created
    When Adding Person Of Contact "John Smith" to Prospect "Building"
    And User clicks on SAVE
    Then As per database "John Smith" is a Person of Contact for Prospect "Building"
    When Removing Person Of Contact "John Smith" from Prospect "Building"
    And User clicks on SAVE
    Then As per database "John Smith" is not a Person of Contact for Prospect "Building"

  Scenario: Removing Address from Prospect and saving to database
    Given Address "First Address" is created
    And Prospect "Building" is created
    When Adding Address "First Address" to Prospect "Building"
    And User clicks on SAVE
    And As per database "First Address" is Address for Prospect "Building"
    When Removing Address "First Address" from Prospect "Building"
    And User clicks on SAVE
    Then As per database "First Address" is not Address for Prospect "Building"

