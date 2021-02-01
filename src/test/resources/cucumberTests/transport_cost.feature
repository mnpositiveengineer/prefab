Feature: Creation and calculation of transport costs

  Acceptance Criteria:
  1. User can specify costs of transport by creating Transport Cost Group (TCG) and providing attributes:
    - cost of one transport (mandatory)
    - distance of transport (optional)
    - type of car (optional)
    - maximum load of one transport in tones (default to 21 tones)
  2. To calculate costs of transport of elements user has to assign elements to TCG.
  3. After assigning elements to TCG following outcomes should be correctly calculated:
    - total weight of elements in the group
    - number of transports in the group (total weight of elements / maximum load of one transport)
    - cost of transport of elements in the group (number of transports * cost of one transport)
  4. User can assign all elements within project to one TCG or split elements to multiply TCGs,
    knowing that different elements have different costs of transport or are produced in a different place.
  5. User can remove one element from the TCG or remove all elements from TCG
  6. When user assigns all elements to one TCG and then reassigns all elements to the secondTCG,
    all elements go to second TCG and there are no elements in first TCG.

  Background: Creating Project
    Given Prospect "Testing Company" is created
    And Project "Testing" is created for Prospect "Testing Company"

  Rule: User can specify costs of transport, assign elements and calculate cost of element transportation

     Scenario: Specifying TCG and assigning elements
       Given Creating Customized Element of attributes
         |name   |amount|volume|area|weight|steel|tension|framework|
         |Beam1  |10    |5     |10  |15    |300  |160    |25       |
         |Beam2  |10    |5     |10  |15    |300  |160    |25       |
       And TCG of following cost is created for Project "Testing"
         |name_of_tcg|cost_of_one_transport|
         |tcg1       |3000                 |
       When Following Elements are assigned to Project "Testing"
         |Beam1|Beam2|
       When All Elements from Project are assigned to TCG "tcg1"
       Then Project "Testing" has 1 TCGs
       And TCG "tcg1" has 2 Elements

    Scenario: Calculating cost of transport of elements in group
       Given Creating Customized Element of attributes
         |name   |amount|volume|area|weight|steel|tension|framework|
         |Beam1  |10    |5     |10  |15    |300  |160    |25       |
         |Beam2  |10    |5     |10  |15    |300  |160    |25       |
        And TCG of following cost is created for Project "Testing"
         |name_of_tcg|cost_of_one_transport|
         |tcg1       |3000                 |
       When Following Elements are assigned to Project "Testing"
         |Beam1|Beam2|
       When All Elements from Project are assigned to TCG "tcg1"
       Then Total weight of elements in TCG "tcg1" is 300
        And Number of transports for TCG "tcg1" is 15
        And Cost of transport of elements in TCG "tcg1" is 45000

      Scenario: Calculating cost of transport of elements in group after changing maximum load of one transport
        Given Creating Customized Element of attributes
          |name   |amount|volume|area|weight|steel|tension|framework|
          |Beam1  |10    |5     |10  |15    |300  |160    |25       |
          |Beam2  |10    |5     |10  |15    |300  |160    |25       |
        And TCG of following cost is created for Project "Testing"
          |name_of_tcg|cost_of_one_transport|
          |tcg1       |3000                 |
        When Setting maximum load of one transport in TCG "tcg1" to 35
        And Following Elements are assigned to Project "Testing"
          |Beam1|Beam2|
        And All Elements from Project are assigned to TCG "tcg1"
        Then Total weight of elements in TCG "tcg1" is 300
        And Number of transports for TCG "tcg1" is 9
        And Cost of transport of elements in TCG "tcg1" is 27000




