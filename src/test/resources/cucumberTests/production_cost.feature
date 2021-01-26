Feature: Production costs - creation and calculation

  Acceptance Criteria
  1. User can specify a production costs of element by assigning the element to a 'production costs group' (PCG).
  2. First user needs to create a PCG for which user specifies following attributes:
    - concrete cost (cost per cubic)
    - steel cost (cost per kg)
    - tension cost (cost per kg)
    - framework cost (cost per square meter)
    - man hour cost (cost per cubic)
    - energy cost (cost per cubic)
    - faculty cost (cost per cubic)
  3. User can specify multiply PCGs within a project, knowing that different elements have different
  costs of production.
  4. User can add an element to selected PCG.
  5. Based on the PCG, user should be able to see following attributes correctly calculated for element:
    - Concrete cost of 1 piece of element
    - Concrete cost od all pieces of element
    - Steel cost of 1 piece of element
    - Steel cost od all pieces of element
    - Tension cost of 1 piece of element
    - Tension cost od all pieces of element
    - Framework cost of 1 piece of element
    - Framework cost od all pieces of element
    - Man hour cost of 1 piece of element
    - Man hour cost od all pieces of element
    - Energy cost of 1 piece of element
    - Energy cost od all pieces of element
    - Faculty cost of 1 piece of element
    - Faculty cost od all pieces of element

#  Scenario: Creating multiple PCGs for a project
#    Given Prospect "Testing PCG" is created
#    And Project "Testing" is created for Prospect "Testing PCG"
#    And Standard Element of name "Column" and amount 10 is created
#    And Customized Element of name "Beam" and amount 2 is created
#    And Console Element of name "Slab" and amount 4 is created
#    When PCG: concrete cost = 300, steel cost = 3.5, tension cost = 4.1, framework cost = 90, man hour cost = 50, energy cost = 50, faculty cost = 80; for Project "Testing" is created
#    Then Project "Testing" has 1 PCGs
#    When PCG: concrete cost = 400, steel cost = 3.6, tension cost = 4.2, framework cost = 100, man hour cost = 60, energy cost = 60, faculty cost = 90; for Project "Testing" is created
#    Then Project "Testing" has 2 PCGs
#    When PCG: concrete cost = 500, steel cost = 3.7, tension cost = 4.3, framework cost = 120, man hour cost = 70, energy cost = 70, faculty cost = 100; for Project "Testing" is created
#    Then Project "Testing" has 3 PCGs