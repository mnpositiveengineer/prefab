Feature: Creating/Modifying and Accessory and assigning it to element

  Acceptance Criteria:
  1. User should be able to create an Accessory. User should be able to define following attributes for Accessory:
    - name (mandatory)
    - unit (mandatory) ---> m|m2|m3|kg|ts|pcs
    - unit price (optional)
  2. Accessory can be added to an element by specifying amount of an accessory

  Scenario: Creating, modifying Accessory and adding it to Element
    Given Standard Element of name "Column" and amount 10 is created
    And Accessory "Bindax" of unit m is created
    And Accessory "Mineral Wood" of unit m3 is created
    When Setting Unit Price of Accessory "Bindax" to 10
    And Setting Unit Price of Accessory "Mineral Wood" to 400
    Then Accessory "Bindax" has Unit Price of value 10
    And Accessory "Mineral Wood" has Unit Price of value 400
    When Adding Accessory "Bindax" of amount 1.8 to Standard Element "Column"
    And Adding Accessory "Mineral Wood" of amount 1.8 to Standard Element "Column"
    Then Standard Element "Column" has 2 Accessories



