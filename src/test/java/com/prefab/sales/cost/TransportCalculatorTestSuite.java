package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.client.Prospect;
import com.prefab.sales.production.*;
import com.prefab.sales.utils.enums.ConsoleOrCutoutType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TransportCalculatorTestSuite {

    Prospect prospect = new Prospect("Testing Company");
    Project project = new Project("Testing Project", prospect);

    StandardElement element1 = new StandardElement("Element 1", 4, "Slab");

    ConsoleElement element2 = new ConsoleElement("Element 2", 5, "Slab");
    ConsoleElement.ConsoleCreator console = element2.createConsole(ConsoleOrCutoutType.console, 2, 0.5F, 0.5F, 0.5F);
    ConsoleElement.ConsoleCreator cutout = element2.createConsole(ConsoleOrCutoutType.cutout, 1, 0.5F, 0.5F, 0.5F);

    CustomizedElement element3 = new CustomizedElement("Element 3", 6, "Beam");

    Transport transport = new Transport(project, 1000);

    @BeforeEach
    void prepareTestData(){
        element1.setHeight(1);
        element1.setWidth(1);
        element1.setLength(1);

        element2.setHeight(2);
        element2.setWidth(2);
        element2.setLength(2);
        element2.addConsoleOrCutout(console);
        element2.addConsoleOrCutout(cutout);

        element3.setWeight(5);

        project.addElement(element1, element2, element3);
    }

    @Test
    void ShouldCalculateTotalWeightOfElements() {
        transport.addAllElementsToGroup();
        Assertions.assertEquals(141.5625, transport.calculate().calculateTotalWeightOfElementsInGroup());
    }

    @Test
    void ShouldCalculateNumberOfCTransports() {
        transport.addAllElementsToGroup();
        Assertions.assertEquals(7, transport.calculate().calculateNumberOfCTransportsInGroup(),0);
        transport.setMaxCarLoadInTones(30);
        Assertions.assertEquals(5, transport.calculate().calculateNumberOfCTransportsInGroup(),0);
    }

    @Test
    void ShouldCalculateCostOfTransport() {
        transport.addAllElementsToGroup();
        Assertions.assertEquals(7000, transport.calculate().calculateCostOfTransportInGroup(),0);
        transport.setMaxCarLoadInTones(30);
        Assertions.assertEquals(5000, transport.calculate().calculateCostOfTransportInGroup(),0);
    }


    @Test
    void ShouldCalculateTotalWeightNumberOfTransportsCostOfTransportForOnlyTwoSelectedElements() {
        Transport newTransport = new Transport(project, 3000);
        Assertions.assertEquals(0, newTransport.calculate().calculateTotalWeightOfElementsInGroup());
        Assertions.assertEquals(0, newTransport.calculate().calculateNumberOfCTransportsInGroup());
        Assertions.assertEquals(0, newTransport.calculate().calculateCostOfTransportInGroup());

        newTransport.addSelectedElementsToGroup(element1, element2);

        Assertions.assertEquals(111.5625, newTransport.calculate().calculateTotalWeightOfElementsInGroup());
        Assertions.assertEquals(6, newTransport.calculate().calculateNumberOfCTransportsInGroup());
        Assertions.assertEquals(18000, newTransport.calculate().calculateCostOfTransportInGroup());
    }
}