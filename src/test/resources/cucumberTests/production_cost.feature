Feature: Production costs - creation and calculation

  Acceptance Criteria
  1. User should be able to specify production costs and calculate production costs of an element.
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
  4. User can specify multiply PCGs within a project, knowing that different elements have different
    costs of production.

  Scenario: Assigning Element to PCG and calculating attributes
    Given Prospect "Testing Company" is created
    And Project "Testing" is created for Prospect "Testing Company"
    And Customized Element of name "Beam" and amount 10 is created
    And PCG "pcg1": concrete cost = 300, steel cost = 3.5, tension cost = 4.1, framework cost = 90, man hour cost = 50, energy cost = 60, faculty cost = 80; for Project "Testing" is created
    When Setting Volume of Customized Element "Beam" to 5
    And Setting Area of Customized Element "Beam" to 10
    And Setting Weight of Customized Element "Beam" to 15
    And Setting Steel of Customized Element "Beam" to 300
    And Setting Tension of Customized Element "Beam" to 160
    And Setting Framework Area of Customized Element "Beam" to 25
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






  #    And Customized Element of name "Beam" and amount 2 is created
#    And Console Element of name "Slab" and amount 4 is created
  #    And PCG: concrete cost = 400, steel cost = 3.6, tension cost = 4.2, framework cost = 100, man hour cost = 60, energy cost = 60, faculty cost = 90; for Project "Testing" is created
#    And PCG: concrete cost = 500, steel cost = 3.7, tension cost = 4.3, framework cost = 120, man hour cost = 70, energy cost = 70, faculty cost = 100; for Project "Testing" is created

