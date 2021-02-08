Feature: Calculation of project costs

  Acceptance Criteria:
  1. Based on Production Cost Groups in project and assigned elements user should see Production Cost of Project
    correctly calculated.
  2. Based on Transport Cost Groups in project and assigned elements user should see Transport Cost of Project
    correctly calculated.
  3. Based on Assembly Cost Groups in project and assigned elements user should see Assembly Cost of Project
    correctly calculated.
  4. Based on Production Cost of Project, Transport Cost of Project and Assembly Cost of Project user should see
    All Cost of Project correctly calculated.

  Background: Creating Project
    Given Prospect "Testing Company" is created
    And Project "Testing" is created for Prospect "Testing Company"

  Rule: Based on Production Cost Groups in project and assigned elements user should see Production Cost of Project
    correctly calculated.

    Scenario: Calculating Production Cost of Project with one PCG
      Given Creating Customized Element of attributes
        |name   |amount|volume|area|weight|steel|tension|framework|
        |Beam1  |10    |5     |10  |15    |300  |160    |25       |
        |Beam2  |10    |5     |10  |15    |300  |160    |25       |
      And PCG of following cost is created for Project "Testing"
        |name_of_pcg   |concrete_cost|steel_cost|tension_cost|framework_cost|man_hour_cost|energy_cost|faculty_cost|
        |pcg1          |300          |3.5       |4.1         |90            |50           |60         |80          |
      When Following Elements are assigned to Project "Testing"
        |Beam1|
        |Beam2|
      And All Elements from Project are assigned to PCG "pcg1"
      Then Production cost of Project "Testing" is 128120

    Scenario: Calculating Production Cost of Project with multiple PCGs
      Given Creating Customized Element of attributes
        |name   |amount|volume|area|weight|steel|tension|framework|
        |Beam1  |10    |5     |10  |15    |300  |160    |25       |
        |Beam2  |10    |5     |10  |15    |300  |160    |25       |
      And PCG of following cost is created for Project "Testing"
        |name_of_pcg   |concrete_cost|steel_cost|tension_cost|framework_cost|man_hour_cost|energy_cost|faculty_cost|
        |pcg1          |300          |3.5       |4.1         |90            |50           |60         |80          |
        |pcg2          |400          |4.5       |5.1         |80            |40           |50         |90          |
      When Following Elements are assigned to Project "Testing"
        |Beam1|
        |Beam2|
      And Following Element is assigned to PCG "pcg1"
        |Beam1|
      And Following Element is assigned to PCG "pcg2"
        |Beam2|
      Then Production cost of Project "Testing" is 134720

  Rule: Based on Transport Cost Groups in project and assigned elements user should see Transport Cost of Project
  correctly calculated.

    Scenario: Calculating Transport Cost of Project with one TCG
      Given Creating Customized Element of attributes
        |name   |amount|volume|area|weight|steel|tension|framework|
        |Beam1  |10    |5     |10  |15    |300  |160    |25       |
        |Beam2  |10    |5     |10  |15    |300  |160    |25       |
      And TCG of following cost is created for Project "Testing"
        |name_of_tcg|cost_of_one_transport|
        |tcg1       |1000                 |
      When Following Elements are assigned to Project "Testing"
        |Beam1|
        |Beam2|
      And All Elements from Project are assigned to TCG "tcg1"
      Then Transport cost of Project "Testing" is 15000

    Scenario: Calculating Transport Cost of Project with multiple PCGs
      Given Creating Customized Element of attributes
        |name   |amount|volume|area|weight|steel|tension|framework|
        |Beam1  |10    |5     |10  |15    |300  |160    |25       |
        |Beam2  |10    |5     |10  |15    |300  |160    |25       |
      And TCG of following cost is created for Project "Testing"
        |name_of_tcg|cost_of_one_transport|
        |tcg1       |1000                 |
        |tcg2       |2000                 |
      When Following Elements are assigned to Project "Testing"
        |Beam1|
        |Beam2|
      And Following Element is assigned to TCG "tcg1"
        |Beam1|
      And Following Element is assigned to TCG "tcg2"
        |Beam2|
      Then Transport cost of Project "Testing" is 24000

  Rule: Based on Assembly Cost Groups in project and assigned elements user should see Assembly Cost of Project
  correctly calculated.

    Scenario: Calculating Assembly Cost of Project with one ACG
      Given Creating Customized Element of attributes
        |name   |amount|volume|area|weight|steel|tension|framework|
        |Beam1  |10    |5     |10  |15    |300  |160    |25       |
        |Beam2  |10    |5     |10  |15    |300  |160    |25       |
      And ACG of following cost is created for Project "Testing"
        |name_of_acg|assembly_cost_one_element|
        |acg1       |1000                     |
      When Following Elements are assigned to Project "Testing"
        |Beam1|
        |Beam2|
      And All Elements from Project are assigned to ACG "acg1"
      Then Assembly cost of Project "Testing" is 20000

    Scenario: Calculating Assembly Cost of Project with multiple PCGs
      Given Creating Customized Element of attributes
        |name   |amount|volume|area|weight|steel|tension|framework|
        |Beam1  |10    |5     |10  |15    |300  |160    |25       |
        |Beam2  |10    |5     |10  |15    |300  |160    |25       |
      And ACG of following cost is created for Project "Testing"
        |name_of_acg|assembly_cost_one_element|
        |acg1       |1000                     |
        |acg2       |2000                     |
      When Following Elements are assigned to Project "Testing"
        |Beam1|
        |Beam2|
      And Following Element is assigned to ACG "acg1"
        |Beam1|
      And Following Element is assigned to ACG "acg2"
        |Beam2|
      Then Assembly cost of Project "Testing" is 30000