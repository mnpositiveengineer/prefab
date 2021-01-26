Feature: Creating, modifying and calculating Console Element

  Acceptance Criteria:
  1. User should be able to create Console Element which is characterized by the same attributes as Standard Element plus
    this element can have console or cutout.
  2. User should be able to create console or cutout. Each console and cutout is characterized by following attributes:
    - amount (mandatory)
    - height (mandatory)
    - width (mandatory)
    - length (mandatory)
  3. Consoles and cutouts can be added to element.
  4. Each console increases volume of element and each cutout decreases volume of element.
  5. Each console increases framework area of element and so do cutout.
  6. Based on the attributes of Console Element, user should see the same attributes as in Standard Element
    correctly calculated.

  Scenario: Adding and removing consoles from element
    Given Console Element of name "Column" and amount 10 is created
    When 1 Consoles of height 0.5, width 0.4, length 0.4 is added to Element of name "Column"
    And 1 Consoles of height 0.4, width 0.5, length 0.4 is added to Element of name "Column"
    Then Console Element of name "Column" has 2 Consoles
    And Console Element of name "Column" has 0 Cutouts
    When 1 Cutout of height 0.2, width 0.2, length 0.2 is added to Element of name "Column"
    Then Console Element of name "Column" has 2 Consoles
    And Console Element of name "Column" has 1 Cutout
    When All Consoles are removed from Element of name "Column"
    Then Console Element of name "Column" has 0 Consoles
    And Console Element of name "Column" has 1 Cutout
    When All Cutouts are removed from Element of name "Column"
    Then Console Element of name "Column" has 0 Consoles
    And Console Element of name "Column" has 0 Cutout

  Scenario Outline: Calculating Console Element
    Given Console Element of name "Column" and amount 10 is created
    And 2 Consoles of height <height1>, width <width1>, length <length1> is added to Element of name "Column"
    And 1 Console of height <height2>, width <width2>, length <length2> is added to Element of name "Column"
    And 1 Cutout of height <height3>, width <width3>, length <length3> is added to Element of name "Column"
    When Setting height of Element "Column" to <height>
    And Setting width of Element "Column" to <width>
    And Setting length of Element "Column" to <length>
    And Setting steel of Element "Column" to <steelSaturation>
    And Setting tension of Element "Column" to <tensionSaturation>
    Then Total Volume of Element "Column" is <volume>
    And Total Area of Element "Column" is <area>
    And Total Weight of Element "Column" is <weight>
    And Total Steel of Element "Column" is <steel>
    And Total Tension of Element "Column" is <tension>
    And Total Framework Area of Element "Column" is <framework>
    Examples:
      | height1 | width1 | length1 | height2 | width2 | length2 | height3 | width3 | length3 | height | width | length | steelSaturation | tensionSaturation | volume | area | weight | steel | tension | framework |
      | 0.5     | 0.5    | 0.5     | 0.4     | 0.4    | 0.4     | 0.3     | 0.3    | 0.3     | 1      | 1     | 5      | 100             | 50                | 52.87  | 50   | 132.175 | 5287 | 2643.5  | 200       |


