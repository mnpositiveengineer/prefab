Feature: Creation and calculation of assembly costs

  Acceptance Criteria:
  1. To define and calculate assembly costs of elements user can create Assembly Cost Group (ACG) and assign elements.
  2. When defining ACG following attribute can be defined:
    - assembly cost of one elements
  3. Based on the attribute defined in ACG, user can see assembly cost of the whole group correctly calculated.
  4. User can create 1 ACG for Project or create multiple ACGs knowing that different elements can have different
    assembly costs.
  5. User can add selected element to ACG or add all elements in Project at once.
  6. User can remove one element from the ACG or remove all elements from ACG
  7. When user assigns all elements to one ACG and then reassigns all elements to the second ACG,
    all elements go to second !CG and there are no elements in first PCG.

  Background: Creating Project
    Given Prospect "Testing Company" is created
    And Project "Testing" is created for Prospect "Testing Company"

  Rule: To define and calculate assembly costs of elements user can create Assembly Cost Group (ACG) and assign elements

  Scenario: Defining ACG within Project and assigning elements
    Given Following "Console" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column3      |20     |0.5    |0.5  |10     |150  |0      |
      |Column4      |10     |0.6    |0.6  |11     |200  |0      |
    And ACG of following cost is created for Project "Testing"
      |name_of_acg|assembly_cost_one_element|
      |acg1       |1000                     |
    When Following Elements are assigned to Project "Testing"
      |Column3|Column4|
    And Following Elements are assigned to ACG "acg1"
      |Column3|Column4|
    Then Project "Testing" has 1 ACGs
    And ACG "acg1" has 2 Element

    Scenario: Calculating assembly costs of elements
      Given Following "Console" Elements are created
        |name         |amount |height |width|length |steel|tension|
        |Column3      |20     |0.5    |0.5  |10     |150  |0      |
      And ACG of following cost is created for Project "Testing"
        |name_of_acg|assembly_cost_one_element|
        |acg1       |1000                     |
      When Following Elements are assigned to ACG "acg1"
        |Column3|
      Then Assembly cost of elements in ACG "acg1" is 20000

  Rule: User can create 1 ACG for Project or create multiple ACGs

    Scenario: Creating multiple ACGs for Project
    Given Following "Console" Elements are created
        |name         |amount |height |width|length |steel|tension|
        |Column3      |20     |0.5    |0.5  |10     |150  |0      |
        |Column4      |10     |0.6    |0.6  |11     |200  |0      |
      And ACG of following cost is created for Project "Testing"
        |name_of_acg|assembly_cost_one_element|
        |acg1       |1000                     |
      And ACG of following cost is created for Project "Testing"
        |name_of_acg|assembly_cost_one_element|
        |acg2       |500                      |
      When Following Elements are assigned to Project "Testing"
        |Column3|Column4|
      And Following Elements are assigned to ACG "acg1"
        |Column3|
      And Following Elements are assigned to ACG "acg2"
        |Column4|
      Then Project "Testing" has 2 ACGs
      And ACG "acg1" has 1 Element
      And ACG "acg2" has 1 Element
