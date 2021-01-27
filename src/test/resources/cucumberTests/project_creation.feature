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
  3. User should be able to add created elements to Project (Standard Elements, Console Elements, Customized Elements)
  4. User should be able to remove created elements from Project.

  Rule: User should be able to specify attributes of Projects presented in Acceptance Criteria

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

    Rule: User should be able to add Elements to Project and remove Elements from Project

    Scenario: Adding Elements to a Project and removing Elements from Project
      Given Prospect "Testing Company" is created
      And Project "Testing" is created for Prospect "Testing Company"
      And Standard Element of name "Column" and amount 10 is created
      And Customized Element of name "Beam" and amount 2 is created
      And Console Element of name "Slab" and amount 4 is created
      When Element "Column" is assigned to Project "Testing"
      Then Project "Testing" has 1 Element
      When Element "Beam" is assigned to Project "Testing"
      Then Project "Testing" has 2 Elements
      When Element "Slab" is assigned to Project "Testing"
      Then Project "Testing" has 3 Elements
      When Element "Column" is removed from Project "Testing"
      Then Project "Testing" has 2 Elements
      When Element "Beam" is removed from Project "Testing"
      Then Project "Testing" has 1 Elements
      When Element "Slab" is removed from Project "Testing"
      Then Project "Testing" has 0 Elements


