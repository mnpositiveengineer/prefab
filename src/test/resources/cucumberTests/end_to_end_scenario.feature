Feature: End to end scenario

  IN PROGRESS...

  Scenario: Creating and calculating an offer
    Given Prospect "Testing Company" is created
    And Project "Testing" is created for Prospect "Testing Company"
    And Following Standard Elements are created
    |name   |amount|height|width|length|steel |tension|
    |Column1|20  |0.5   |0.5  |10    |150             |0     |
    |Column2|10  |0.6   |0.6  |11    |200             |0     |
    |Girder1|14  |1   |0.5  |16    |300             |150     |
    |Girder2|4  |1.2   |0.6  |16    |300             |150     |
    |SandwichWall|80  |0.4   |1.2  |5    |80             |0     |
    |Slab|90  |0.2   |0.5  |10    |150             |20     |
    When Following Standard Elements are assigned to Project "Testing"
    |Column1|
    |Column2|
    |Girder1|
    |Girder2|
    |SandwichWall|
    |Slab|
    Then Project "Testing" has 6 Elements