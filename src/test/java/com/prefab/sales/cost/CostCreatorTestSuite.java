package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.client.Prospect;
import com.prefab.sales.production.ConsoleElement;
import com.prefab.sales.production.CustomizedElement;
import com.prefab.sales.production.StandardElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class CostCreatorTestSuite {

    Prospect prospect = new Prospect("Testing Company");
    Project project = new Project("Testing Project", prospect);

    StandardElement element1 = new StandardElement("Element 1", 4, "Slab");
    ConsoleElement element2 = new ConsoleElement("Element 2", 5, "Slab");
    CustomizedElement element3 = new CustomizedElement("Element 3", 6, "Beam");

    Transport transport1 = new Transport(project, 1000);
    Transport transport2 = new Transport(project, 2000);

    Production productionCost1 = new Production(project, 250, 3.4F, 4, 90, 50, 50, 50);
    Production productionCost2 = new Production(project, 260, 3.8F, 5, 100, 60, 50, 50);

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void prepareTestData(){
        project.addElement(element1, element2, element3);
    }

    @Test
    void ShouldAddAllElementsToGroup() {
        //transport
        Assertions.assertEquals(0, project.getTransportGroups().size());
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());
        Assertions.assertEquals(0, transport2.getElementsInGroup().size());
        //production
        Assertions.assertEquals(0, project.getProductionGroups().size());
        Assertions.assertEquals(0, productionCost1.getElementsInGroup().size());
        Assertions.assertEquals(0, productionCost2.getElementsInGroup().size());

        transport1.addAllElementsToGroup();
        productionCost1.addAllElementsToGroup();

        //transport
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(3, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(3, transport1.getElementsInGroup().size());
        Assertions.assertEquals(0, transport2.getElementsInGroup().size());
        //production
        Assertions.assertEquals(1, project.getProductionGroups().size());
        Assertions.assertEquals(3, project.getProductionGroups().get(productionCost1).size());
        Assertions.assertEquals(3, productionCost1.getElementsInGroup().size());
        Assertions.assertEquals(0, productionCost2.getElementsInGroup().size());

        transport2.addAllElementsToGroup();
        productionCost2.addAllElementsToGroup();

        //transport
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(3, project.getTransportGroups().get(transport2).size());
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());
        Assertions.assertEquals(3, transport2.getElementsInGroup().size());
        //production
        Assertions.assertEquals(1, project.getProductionGroups().size());
        Assertions.assertEquals(3, project.getProductionGroups().get(productionCost2).size());
        Assertions.assertEquals(0, productionCost1.getElementsInGroup().size());
        Assertions.assertEquals(3, productionCost2.getElementsInGroup().size());
    }

    @Test
    void ShouldRemoveAllElementsFromTransport() {
        //transport
        transport1.addSelectedElementsToGroup(element1, element2);
        transport2.addSelectedElementsToGroup(element3);
        transport1.removeAllElementsFromGroup();
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(1, project.getTransportGroups().get(transport2).size());

        //production
        productionCost1.addSelectedElementsToGroup(element1, element2);
        productionCost2.addSelectedElementsToGroup(element3);
        productionCost2.removeAllElementsFromGroup();
        Assertions.assertEquals(0, productionCost2.getElementsInGroup().size());
        Assertions.assertEquals(1, project.getProductionGroups().size());
        Assertions.assertEquals(2, project.getProductionGroups().get(productionCost1).size());

    }

    @Test
    void ShouldAddSelectedElementsToTransport() {
        transport1.addSelectedElementsToGroup(element1, element2);
        transport2.addSelectedElementsToGroup(element3);
        Assertions.assertEquals(2, project.getTransportGroups().size());
        Assertions.assertEquals(2, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(1, project.getTransportGroups().get(transport2).size());
    }

    @Test
    void ShouldThrowExceptionWhenAddingDuplicatedElement() {
        System.setOut(new PrintStream(outputStreamCaptor));
        transport1.addSelectedElementsToGroup(element1, element2);
        transport1.addSelectedElementsToGroup(element1);
        Assertions.assertEquals("Element 1 is already assigned to transport.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();
        transport1.addSelectedElementsToGroup(element2);
        Assertions.assertEquals("Element 2 is already assigned to transport.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void ShouldRemoveSelectedElementsFromTransport() {
        transport1.addAllElementsToGroup();

        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(3, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(3, transport1.getElementsInGroup().size());

        transport1.removeSelectedElementsFromGroup(element1);

        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(2, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(2, transport1.getElementsInGroup().size());

        transport1.removeSelectedElementsFromGroup(element2);

        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(1, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(1, transport1.getElementsInGroup().size());

        transport1.removeSelectedElementsFromGroup(element3);

        Assertions.assertEquals(0, project.getTransportGroups().size());
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());
    }

    @Test
    void ShouldThrowExceptionWhenRemovingSelectedElements() {
        System.setOut(new PrintStream(outputStreamCaptor));
        transport1.removeSelectedElementsFromGroup(element1);
        Assertions.assertEquals("Element 1 is not on the list.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();
        transport1.addSelectedElementsToGroup(element1, element2);
        transport1.removeSelectedElementsFromGroup(element3);
        Assertions.assertEquals("Element 3 is not on the list.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void ShouldAdjustProjectTransportListWhenCombiningMultipleTransportCreationOperation() {
        // no element added to any transport
        Assertions.assertEquals(0, project.getTransportGroups().size());
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());
        Assertions.assertEquals(0, transport2.getElementsInGroup().size());

        // all 3 elements added to transport1
        transport1.addAllElementsToGroup();
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(3, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(0, transport2.getElementsInGroup().size());
        Assertions.assertEquals(3, transport1.getElementsInGroup().size());

        //all 3 elements added to transport2
        transport2.addAllElementsToGroup();
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(3, project.getTransportGroups().get(transport2).size());
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());
        Assertions.assertEquals(3, transport2.getElementsInGroup().size());

        //all 3 elements removed from transport1
        transport1.removeAllElementsFromGroup();
        Assertions.assertEquals(3, project.getTransportGroups().get(transport2).size());
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());
        Assertions.assertEquals(3, transport2.getElementsInGroup().size());

        //all 3 elements removed from transport2
        transport2.removeAllElementsFromGroup();
        Assertions.assertEquals(0, project.getTransportGroups().size());
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());
        Assertions.assertEquals(0, transport2.getElementsInGroup().size());

        //2 elements added to transport1
        transport1.addSelectedElementsToGroup(element1, element2);
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(2, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(0, transport2.getElementsInGroup().size());
        Assertions.assertEquals(2, transport1.getElementsInGroup().size());

        //duplicated element added to transport 2
        System.setOut(new PrintStream(outputStreamCaptor));
        transport2.addSelectedElementsToGroup(element1);
        Assertions.assertEquals("Element 1 is already assigned to transport.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();
        transport2.addSelectedElementsToGroup(element2);
        Assertions.assertEquals("Element 2 is already assigned to transport.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(2, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(0, transport2.getElementsInGroup().size());
        Assertions.assertEquals(2, transport1.getElementsInGroup().size());

        //1 element added to transport 2
        transport2.addSelectedElementsToGroup(element3);
        Assertions.assertEquals(2, project.getTransportGroups().size());
        Assertions.assertEquals(2, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(1, project.getTransportGroups().get(transport2).size());
        Assertions.assertEquals(1, transport2.getElementsInGroup().size());
        Assertions.assertEquals(2, transport1.getElementsInGroup().size());

        //1 element removed from transport 1
        transport1.removeSelectedElementsFromGroup(element1);
        Assertions.assertEquals(2, project.getTransportGroups().size());
        Assertions.assertEquals(1, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(1, project.getTransportGroups().get(transport2).size());
        Assertions.assertEquals(1, transport2.getElementsInGroup().size());
        Assertions.assertEquals(1, transport1.getElementsInGroup().size());

        //1 element that does not exist removed from transport 2
        transport2.removeSelectedElementsFromGroup(element1);
        Assertions.assertEquals("Element 1 is not on the list.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();

        //last element removed from transport 1
        transport1.removeSelectedElementsFromGroup(element2);
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(1, project.getTransportGroups().get(transport2).size());
        Assertions.assertEquals(1, transport2.getElementsInGroup().size());
        Assertions.assertEquals(0, transport1.getElementsInGroup().size());

        //all elements assigned to transport 1
        transport1.addAllElementsToGroup();
        Assertions.assertEquals(1, project.getTransportGroups().size());
        Assertions.assertEquals(3, project.getTransportGroups().get(transport1).size());
        Assertions.assertEquals(0, transport2.getElementsInGroup().size());
        Assertions.assertEquals(3, transport1.getElementsInGroup().size());
    }

}