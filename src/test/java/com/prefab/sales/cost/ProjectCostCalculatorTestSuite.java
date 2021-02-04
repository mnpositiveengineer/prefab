package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.client.Prospect;
import com.prefab.sales.production.*;
import com.prefab.sales.utils.enums.ConsoleOrCutoutType;
import com.prefab.sales.utils.enums.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectCostCalculatorTestSuite {

    Prospect prospect = new Prospect("Testing Company");
    Project project = new Project("Testing Project", prospect);

    StandardElement element1 = new StandardElement("Element 1", 4, "Slab");
    ConsoleElement element2 = new ConsoleElement("Element 2", 5, "Slab");
    CustomizedElement element3 = new CustomizedElement("Element 3", 6, "Beam");

    Accessory accessory1 = new Accessory("Bindax", Unit.m);
    Accessory accessory2 = new Accessory("Pipe", Unit.m);
    Accessory accessory3 = new Accessory("Styrofoam", Unit.m3);
    Accessory accessory4 = new Accessory("Mineral Wool", Unit.m3);

    ConsoleElement.ConsoleCreator console = element2.createConsole(ConsoleOrCutoutType.console, 2, 0.5F, 0.5F, 0.5F);
    ConsoleElement.ConsoleCreator cutout = element2.createConsole(ConsoleOrCutoutType.cutout, 1, 0.5F, 0.5F, 0.5F);


    Production productionCost1 = new Production(project, 250, 3.4F, 4, 90, 50, 50, 50);
    Production productionCost2 = new Production(project, 260, 3.8F, 5, 100, 60, 50, 50);
    Production productionCost3 = new Production(project, 270, 3.9F, 6, 120, 90, 50, 50);

    Transport transport1 = new Transport(project, 1000);
    Transport transport2 = new Transport(project, 2000);

    Assembly assembly1 = new Assembly(project, 1000);
    Assembly assembly2 = new Assembly(project, 2000);

    ProjectCostCalculator calculator = new ProjectCostCalculator(project);

    @BeforeEach
    void prepareTestData(){
        element1.setHeight(1);
        element1.setWidth(1);
        element1.setLength(1);
        element1.setSteelSaturation(100);
        element1.setTensionSaturation(50);

        element2.setHeight(2);
        element2.setWidth(2);
        element2.setLength(2);
        element2.setSteelSaturation(120);
        element2.setTensionSaturation(70);
        element2.addConsoleOrCutout(console, cutout);
        element2.addAccessory(accessory1, 3F);
        accessory1.setUnitPrice(20);
        element2.addAccessory(accessory2, 1.8F);

        element3.setVolume(2);
        element3.setWeight(7);
        element3.setSteelWeight(200);
        element3.setTensionWeight(150);
        element3.setFrameworkArea(10);
        element3.setVolumeOfCutouts(1);
        element3.setVolumeOfConsoles(2);
        element3.addAccessory(accessory3, 0.5F);
        accessory3.setUnitPrice(150);
        element3.addAccessory(accessory4, 0.6F);
        accessory4.setUnitPrice(300);

        project.addElement(element1, element2, element3);

        productionCost1.addSelectedElementsToGroup(element1);
        productionCost2.addSelectedElementsToGroup(element2);
        productionCost3.addSelectedElementsToGroup(element3);

        transport1.addSelectedElementsToGroup(element1, element2);
        transport2.addSelectedElementsToGroup(element3);
        transport2.setMaxCarLoadInTones(35);

        assembly1.addSelectedElementsToGroup(element1, element2);
        assembly2.addSelectedElementsToGroup(element3);
    }

    @Test
    void ShouldCalculateAllProductionCostsOfProject() {
        Assertions.assertEquals(94256.25, calculator.calculateAllProductionCostsOfProject());
    }

    @Test
    void ShouldCalculateAllTransportCostsOfProject() {
        Assertions.assertEquals(10000, calculator.calculateAllTransportCostsOfProject());
    }

    @Test
    void ShouldCalculateAllAssemblyCostsOfProject() {
        Assertions.assertEquals(21000, project.calculate().calculateAllAssemblyCostsOfProject());
    }




}