Feature: End to end scenario

  IN PROGRESS...

  Scenario: Creating and calculating an offer
    Given Prospect "Testing Company" is created
    And Project "Testing" is created for Prospect "Testing Company"
    And Following "Standard" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column1      |20     |0.5    |0.5  |10     |150  |0      |
      |Column2      |10     |0.6    |0.6  |11     |200  |0      |
      |Girder1      |14     |1      |0.5  |16     |300  |150    |
      |Girder2      |4      |1.2    |0.6  |16     |300  |150    |
      |SandwichWall |80     |0.4    |1.2  |5      |80   |0      |
      |Slab         |90     |0.2     |0.5 |10     |150  |20     |
    And Following "Console" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column3      |20     |0.5    |0.5  |10     |150  |0      |
      |Column4      |10     |0.6    |0.6  |11     |200  |0      |
      |Girder3      |14     |1      |0.5  |16     |300  |150    |
      |Girder4      |4      |1.2    |0.6  |16     |300  |150    |
      |Beam1        |80     |0.4    |1.2  |5      |80   |0      |
      |Beam2        |90     |0.2     |0.5 |10     |150  |20     |
    And Following "Customized" Elements are created
      |name         |amount |height |width|length |steel|tension|
      |Column5      |20     |0.5    |0.5  |10     |150  |0      |
      |Column6      |10     |0.6    |0.6  |11     |200  |0      |
      |Girder5      |14     |1      |0.5  |16     |300  |150    |
      |Girder6      |4      |1.2    |0.6  |16     |300  |150    |
      |Beam3        |80     |0.4    |1.2  |5      |80   |0      |
      |Beam4        |90     |0.2     |0.5 |10     |150  |20     |
    When Following Elements are assigned to Project "Testing"
      |Column1|
      |Column2|
      |Girder1|
      |Girder2|
      |SandwichWall|
      |Slab|
      |Column3|
      |Column4|
      |Girder3|
      |Girder4|
      |Beam1|
      |Beam2|
      |Column5|
      |Column6|
      |Girder5|
      |Girder6|
      |Beam3|
      |Beam4|
    Then Project "Testing" has 18 Elements