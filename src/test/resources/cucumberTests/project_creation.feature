Feature: Project creation and modification

  Acceptance Criteria:
  1. User should be able to create a Project for existing Prospect. To create a Project user needs to specify Prospect
      and Name od the Project.
  2. User should be able to specify/modify following attributes for the Project:
    - Prospect (mandatory when creating)
    - Name (mandatory when creating)
    - Address
    - Construction Type (Warehouse|Infrastructure|House|Factory|Bridge|Stadium|Tunnel)
    - Project Type (Assembly|Delivery)
    - Client Type (Investor|General|Subcontractor)
    - Is Design Included (True/False)
    - Project Status (Received|In_progress|Offer_sent|Discarded|Approved)

  Scenario: Creating Project and modifying data
    Given Prospect "Testing Company" is created
    And Address "Address" is created
    And Project "Testing" is created for Prospect "Testing Company"
    When Adding "Address" to Project "Testing"
    Then Project "Testing" has Address "Address"
    When Setting Construction Type as House in Project "Testing"
    Then Construction Type of Project "Testing" is House
    When Setting Project Type as Assembly of Project "Testing"
    Then Project Type of Project "Testing" is Assembly
    When Setting Client Type as Investor in Project "Testing"
    Then Client Type of Project "Testing" is Investor
    When Setting that Design is included in Project "Testing"
    Then Design is included in Project "Testing"
    When Setting Status of Project "Testing" to Received
    Then Status of Project "Testing" is Received
    When Changing Name of Project "Testing" to "Building"
    Then Name of Project "Building" is "Building"
