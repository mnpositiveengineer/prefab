Feature: Creating, modifying and calculating of Customized Element

  Acceptance Criteria
  1. User should be able to create a Customized Element and specify following attributes:
  - volume of 1 piece of element
  - area of 1 piece of element
  - weight of 1 piece of element
  - steel weight of 1 piece of element
  - tension weight of 1 piece of element
  - framework area of 1 piece of element
  2. Besides above attributes, Customized Element can have the same attributes as Standard Element (width, height,
    accessories, amount, name etc.).
  3. Based on the customized attributes, user should be able to see following attributes correctly calculated:
  - total volume of all pieces of element
  - total area of all pieces of element
  - total weight of all pieces of element
  - total steel weight of all pieces of element
  - total tension weight of all pieces of element
  - total framework area of all pieces of element

  Scenario: Creating and calculating Customized Element
    Given Customized Element of name "Beam" and amount 6 is created
    When Setting Volume of Customized Element "Beam" to 5
    And Setting Area of Customized Element "Beam" to 10
    And Setting Weight of Customized Element "Beam" to 15
    And Setting Steel of Customized Element "Beam" to 300
    And Setting Tension of Customized Element "Beam" to 160
    And Setting Framework Area of Customized Element "Beam" to 25
    Then Total Volume of Element "Beam" is 30
    Then Total Area of Element "Beam" is 60
    Then Total Steel of Element "Beam" is 1800
    Then Total Tension of Element "Beam" is 960
    Then Total Weight of Element "Beam" is 90
    Then Total Framework Area of Element "Beam" is 150