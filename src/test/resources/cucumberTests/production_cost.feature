Feature: Production costs - creation and calculation

  Acceptance Criteria
  1. User can specify production costs and calculate production costs of an element.
  2. User can create a 'production costs group' (PCG) for which user specifies following attributes:
    - concrete cost (cost per cubic)
    - steel cost (cost per kg)
    - tension cost (cost per kg)
    - framework cost (cost per square meter)
    - man hour cost (cost per cubic)
    - energy cost (cost per cubic)
    - faculty cost (cost per cubic)
  3. Based on the PCG's attributes, user should be able to see following attributes correctly calculated for element:
    - Concrete cost of 1 piece of element
    - Concrete cost od all pieces of element
    - Steel cost of 1 piece of element
    - Steel cost od all pieces of element
    - Tension cost of 1 piece of element
    - Tension cost od all pieces of element
    - Framework cost of 1 piece of element
    - Framework cost od all pieces of element
    - Energy and Labour cost (Man hour cost + energy cost + faculty cost) of 1 piece of element
    - Energy and Labour cost (Man hour cost + energy cost + faculty cost) of all pieces of element
  4. For elements assigned to Project user can specify one PCG or multiply PCGs, knowing that different elements
      can have different PCGs.
  5. User can remove one element from the PCG or remove all elements from PCG
  6. When user assigns all elements to one PCG and then reassigns all elements to the second PCG,
    all elements go to second PCG and there are no elements in first PCG.

  Background: Creating Project
    Given Prospect "Testing Company" is created
    And Project "Testing" is created for Prospect "Testing Company"

  Rule: User can specify production costs and calculate production costs of an element.

  Scenario: Assigning Element to PCG and calculating attributes
    Given Creating Customized Element of attributes
    |name   |amount|volume|area|weight|steel|tension|framework|
    |Beam   |10    |5     |10  |15    |300  |160    |25       |
    And PCG of following cost is created for Project "Testing"
    |name_of_pcg   |concrete_cost|steel_cost|tension_cost|framework_cost|man_hour_cost|energy_cost|faculty_cost|
    |pcg1          |300          |3.5       |4.1         |90            |50           |60         |80          |
    Then Based on PCG "pcg1" Concrete cost of 1 piece of Element "Beam" is 1500
    And Based on PCG "pcg1" Concrete cost of all pieces of Element "Beam" is 15000
    And Based on PCG "pcg1" Steel cost of 1 piece of Element "Beam" is 1050
    And Based on PCG "pcg1" Steel cost of all pieces of Element "Beam" is 10500
    And Based on PCG "pcg1" Tension cost of 1 piece of Element "Beam" is 656
    And Based on PCG "pcg1" Tension cost of all pieces of Element "Beam" is 6560
    And Based on PCG "pcg1" Framework cost of 1 piece of Element "Beam" is 2250
    And Based on PCG "pcg1" Framework cost of all pieces of Element "Beam" is 22500
    And Based on PCG "pcg1" Labour and Energy cost of 1 piece of Element "Beam" is 950
    And Based on PCG "pcg1" Labour and Energy cost of all pieces of Element "Beam" is 9500

  Rule: For elements assigned to Project user can specify one PCG or multiply PCGs

  Scenario: Assigning all Elements in Project to one PCG
    Given Following "Standard" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column1      |20     |0.5    |0.5  |10     |150  |0      |
      |Column2      |10     |0.6    |0.6  |11     |200  |0      |
    And Following "Console" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column3      |20     |0.5    |0.5  |10     |150  |0      |
      |Column4      |10     |0.6    |0.6  |11     |200  |0      |
    And Following "Customized" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column5      |20     |0.5    |0.5  |10     |150  |0      |
      |Column6      |10     |0.6    |0.6  |11     |200  |0      |
    And PCG of following costs is created for Project "Testing"
      |name_of_pcg   |concrete_cost|steel_cost|tension_cost|framework_cost|man_hour_cost|energy_cost|faculty_cost|
      |pcg1          |300          |3.5       |4.1         |90            |50           |60         |80          |
    When Following Elements are assigned to Project "Testing"
      |Column1|
      |Column2|
      |Column3|
      |Column4|
      |Column5|
      |Column6|
    And All Elements from Project are assigned to PCG "pcg1"
    Then Project "Testing" has 1 PCGs
    And PCG "pcg1" has 6 Elements

  Scenario: Assigning Elements in Project to 3 PCG
    Given Following "Standard" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column1      |20     |0.5    |0.5  |10     |150  |0      |
      |Column2      |10     |0.6    |0.6  |11     |200  |0      |
    And Following "Console" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column3      |20     |0.5    |0.5  |10     |150  |0      |
      |Column4      |10     |0.6    |0.6  |11     |200  |0      |
    And Following "Customized" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column5      |20     |0.5    |0.5  |10     |150  |0      |
      |Column6      |10     |0.6    |0.6  |11     |200  |0      |
    And PCG of following costs is created for Project "Testing"
      |name_of_pcg   |concrete_cost|steel_cost|tension_cost|framework_cost|man_hour_cost|energy_cost|faculty_cost|
      |pcg1          |300          |3.5       |4.1         |90            |50           |60         |80          |
      |pcg2          |400          |3.6       |4.2         |100           |60           |70         |90          |
      |pcg3          |500          |3.7       |4.3         |110           |70           |80         |70          |
    When Following Elements are assigned to Project "Testing"
      |Column1|Column2|Column3|Column4|Column5|Column6|
    And Following Elements are assigned to PCG "pcg1"
      |Column1|Column2|
    Then Project "Testing" has 1 PCGs
    And PCG "pcg1" has 2 Elements
    When Following Elements are assigned to PCG "pcg2"
      |Column3|Column4|
    Then Project "Testing" has 2 PCGs
    And PCG "pcg2" has 2 Elements
    When Following Elements are assigned to PCG "pcg3"
      |Column5|Column6|
    Then Project "Testing" has 3 PCGs
    And PCG "pcg3" has 2 Elements

    Rule: User can remove one element from the PCG or remove all elements from PCG

   Scenario: Removing one element from PCG
     Given Following "Standard" Elements are created
       |name         |amount |height |width|length |steel|tension|
       |Column1      |20     |0.5    |0.5  |10     |150  |0      |
       |Column2      |10     |0.6    |0.6  |11     |200  |0      |
     And PCG of following costs is created for Project "Testing"
       |name_of_pcg   |concrete_cost|steel_cost|tension_cost|framework_cost|man_hour_cost|energy_cost|faculty_cost|
       |pcg1          |300          |3.5       |4.1         |90            |50           |60         |80          |
     When Following Elements are assigned to Project "Testing"
       |Column1|
       |Column2|
     And All Elements from Project are assigned to PCG "pcg1"
     Then Project "Testing" has 1 PCGs
     And PCG "pcg1" has 2 Elements
     When Following Element is removed from PCG "pcg1"
       |Column1|
     Then Project "Testing" has 1 PCGs
     And PCG "pcg1" has 1 Element
     When Following Element is removed from PCG "pcg1"
       |Column2|
     Then Project "Testing" has 0 PCGs
     And PCG "pcg1" has 0 Element

     Rule: Assigning all elements to another PCG clears all elements from the first PCG

   Scenario:
     Given Following "Standard" Elements are created
       |name         |amount |height |width|length |steel|tension|
       |Column1      |20     |0.5    |0.5  |10     |150  |0      |
       |Column2      |10     |0.6    |0.6  |11     |200  |0      |
     And PCGs of following costs are created for Project "Testing"
       |name_of_pcg   |concrete_cost|steel_cost|tension_cost|framework_cost|man_hour_cost|energy_cost|faculty_cost|
       |pcg1          |300          |3.5       |4.1         |90            |50           |60         |80          |
       |pcg2          |300          |3.5       |4.1         |90            |50           |60         |80          |
     When Following Elements are assigned to Project "Testing"
       |Column1|Column2|
     And All Elements from Project are assigned to PCG "pcg1"
     Then PCG "pcg1" has 2 Element
     And PCG "pcg2" has 0 Element
     When All Elements from Project are assigned to PCG "pcg2"
     Then PCG "pcg1" has 0 Element
     Then PCG "pcg2" has 2 Element