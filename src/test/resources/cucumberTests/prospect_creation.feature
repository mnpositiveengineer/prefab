Feature: Prospect creation and modification

  Acceptance Criteria:
  1. User should be able to create a Prospect and assign multiple Persons of Contact and Addresses to the Prospect.
  2. User should be able to remove Persons of Contact and Addresses from the Prospect.
  3. User should be able to set and modify following attributes of Prospect:
    - Principal Activity
    - Tax ID
    - Name

  Background:
    Given Prospect "Testing" is created
    And Person Of Contact "First Person" is created
    And Address "First Address" is created
    And Person Of Contact "Second Person" is created
    And Address "Second Address" is created

  Scenario: Adding and removing Person Of Contact and Address
    When Adding Person Of Contact "First Person" to Prospect "Testing"
      And Adding Address "First Address" to Prospect "Testing"
    Then Prospect "Testing" has 1 Person Of Contact
      And Prospect "Testing" has 1 Address
    When Adding Person Of Contact "Second Person" to Prospect "Testing"
      And Adding Address "Second Address" to Prospect "Testing"
    Then Prospect "Testing" has 2 Persons Of Contact
      And Prospect "Testing" has 2 Addresses
    When Removing Person Of Contact "First Person" from Prospect "Testing"
      And Removing Address "First Address" from Prospect "Testing"
    Then Prospect "Testing" has 1 Person Of Contact
      And Prospect "Testing" has 1 Address
    When Removing Person Of Contact "Second Person" from Prospect "Testing"
      And Removing Address "Second Address" from Prospect "Testing"
    Then Prospect "Testing" has 0 Person Of Contact
      And Prospect "Testing" has 0 Address

  Scenario: Modifying Prospect's data
    When Setting Principal Activity of Prospect "Testing" to "Testing activity"
    Then Principal Activity of Prospect "Testing" is set to "Testing activity"
    When Setting Tax ID of Prospect "Testing" to "123456789"
    Then Tax ID of Prospect "Testing" is set to "123456789"
    When Changing Name of Prospect "Testing" to "Building"
    Then Name of Prospect "Building" is "Building"
    When Setting Principal Activity of Prospect "Building" to "Building activity"
    Then Principal Activity of Prospect "Building" is set to "Building activity"