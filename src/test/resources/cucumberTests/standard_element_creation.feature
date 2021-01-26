Feature: Creating, modifying and calculating Standard Element

  Acceptance Criteria:
  1. User should be able to create a standard element and provide following attributes:
    - name (mandatory when creating)
    - amount (mandatory when creating)
    - height
    - width
    - length
    - steel saturation
    - tension
  2. Anytime user should be able to amend value of any attribute
  3. Based on the attributes above, user should be able to see following attributes correctly calculated:
    - volume of 1 piece of element
    - total volume of all pieces of element
    - area of 1 piece of element
    - total area of all pieces of element
    - weight of 1 piece of element
    - total weight of all pieces of element
    - steel weight of 1 piece of element
    - total steel weight of all pieces of element
    - tension weight of 1 piece of element
    - total tension weight of all pieces of element
    - framework area of 1 piece of element
    - total framework area of all pieces of element

  Scenario Outline: Creating, modifying and calculating Standard Element
    Given Standard Element of name "Column" and amount 10 is created
    When Setting amount of Element "Column" to <amount>
      And Setting height of Element "Column" to <height>
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
    When Setting length of Element "Column" to <length2>
      And Setting amount of Element "Column" to <amount2>
      And Setting tension of Element "Column" to <tensionSaturation2>
    Then Total Volume of Element "Column" is <volume2>
      And Total Area of Element "Column" is <area2>
      And Total Weight of Element "Column" is <weight2>
      And Total Tension of Element "Column" is <tension2>
      And Total Framework Area of Element "Column" is <framework2>
    Examples:
      | amount | height | width | length | steelSaturation | tensionSaturation | volume | area | weight | steel | tension | framework | length2 | amount2 | tensionSaturation2 | volume2 | area2 | weight2 | tension2 | framework2 |
      | 10 | 0.5 | 0.4 | 5 | 100 | 50 | 10 | 20 | 25 | 1000 | 500 | 74 | 7 | 5 | 80 | 7 | 14 | 17.5 | 560 | 51 |
      | 20 | 0.6 | 0.5 | 10 | 120 | 0 | 60 | 100 | 150 | 7200 | 0 | 352 | 11 | 21 | 10 | 69.3 | 115.5 | 173.25 | 693 | 405.3 |