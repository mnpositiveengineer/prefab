package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.client.Prospect;
import com.prefab.sales.production.*;
import com.prefab.sales.utils.enums.ConsoleOrCutoutType;
import com.prefab.sales.utils.enums.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductionCostCalculatorTestSuite {

    Prospect prospect = new Prospect("Testing Company");
    Project project = new Project("Testing project", prospect);

    StandardElement element1 = new StandardElement("Element 1", 4, "Slab");
    ConsoleElement element2 = new ConsoleElement("Element 2", 5, "Slab");
    CustomizedElement element3 = new CustomizedElement("Element 3", 6, "Beam");

    ConsoleElement.ConsoleCreator console = element2.createConsole(ConsoleOrCutoutType.console, 2, 0.5F, 0.5F, 0.5F);
    ConsoleElement.ConsoleCreator cutout = element2.createConsole(ConsoleOrCutoutType.cutout, 1, 0.5F, 0.5F, 0.5F);
    Accessory accessory1 = new Accessory("Bindax", Unit.m);
    Accessory accessory2 = new Accessory("Pipe", Unit.m);


    Accessory accessory3 = new Accessory("Styrofoam", Unit.m3);
    Accessory accessory4 = new Accessory("Mineral Wool", Unit.m3);

    Production productionCost1 = new Production(project, 250, 3.4F, 4, 90, 50, 50, 50);
    Production productionCost2 = new Production(project, 260, 3.8F, 5, 100, 60, 50, 50);
    Production productionCost3 = new Production(project, 270, 3.9F, 6, 120, 90, 50, 50);

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
        element3.setSteelWeight(200);
        element3.setTensionWeight(150);
        element3.setFrameworkArea(10);
        element3.setVolumeOfCutouts(1);
        element3.setVolumeOfConsoles(2);
        element3.addAccessory(accessory3, 0.5F);
        accessory3.setUnitPrice(150);
        element3.addAccessory(accessory4, 0.6F);
        accessory4.setUnitPrice(300);
    }

    @Test
    void shouldCalculateCostOfConcreteOfOnePiece() {
        Assertions.assertEquals(250, productionCost1.calculate().calculateConcreteCostOfOnePiece(element1));
        Assertions.assertEquals(2112.5, productionCost2.calculate().calculateConcreteCostOfOnePiece(element2));
        Assertions.assertEquals(810, productionCost3.calculate().calculateConcreteCostOfOnePiece(element3));
    }

    @Test
    void shouldCalculateCostOfConcreteOfAllPieces() {
        Assertions.assertEquals(1000, productionCost1.calculate().calculateConcreteCostOfAllPieces(element1));
        Assertions.assertEquals(10562.5, productionCost2.calculate().calculateConcreteCostOfAllPieces(element2));
        Assertions.assertEquals(4860, productionCost3.calculate().calculateConcreteCostOfAllPieces(element3));
    }

    @Test
    void shouldCalculateSteelCostOfOnePiece() {
        Assertions.assertEquals(340, productionCost1.calculate().calculateSteelCostOfOnePiece(element1));
        Assertions.assertEquals(3705, productionCost2.calculate().calculateSteelCostOfOnePiece(element2));
        Assertions.assertEquals(780, productionCost3.calculate().calculateSteelCostOfOnePiece(element3));
    }

    @Test
    void shouldCalculateSteelCostOfAllPieces() {
        Assertions.assertEquals(1360, productionCost1.calculate().calculateSteelCostOfAllPieces(element1));
        Assertions.assertEquals(18525, productionCost2.calculate().calculateSteelCostOfAllPieces(element2));
        Assertions.assertEquals(4680, productionCost3.calculate().calculateSteelCostOfAllPieces(element3));
    }

    @Test
    void shouldCalculateTensionCostOfOnePiece() {
        Assertions.assertEquals(200, productionCost1.calculate().calculateTensionCostOfOnePiece(element1));
        Assertions.assertEquals(2843.75, productionCost2.calculate().calculateTensionCostOfOnePiece(element2));
        Assertions.assertEquals(900, productionCost3.calculate().calculateTensionCostOfOnePiece(element3));
    }

    @Test
    void shouldCalculateTensionCostOfAllPieces() {
        Assertions.assertEquals(800, productionCost1.calculate().calculateTensionCostOfOAllPieces(element1));
        Assertions.assertEquals(14218.75, productionCost2.calculate().calculateTensionCostOfOAllPieces(element2));
        Assertions.assertEquals(5400, productionCost3.calculate().calculateTensionCostOfOAllPieces(element3));
    }

    @Test
    void shouldCalculateFrameworkCostOfOnePiece() {
        Assertions.assertEquals(450, productionCost1.calculate().calculateFrameworkCostOfOnePiece(element1));
        Assertions.assertEquals(2300, productionCost2.calculate().calculateFrameworkCostOfOnePiece(element2));
        Assertions.assertEquals(1200, productionCost3.calculate().calculateFrameworkCostOfOnePiece(element3));
    }

    @Test
    void shouldCalculateFrameworkCostOfAllPieces() {
        Assertions.assertEquals(1800, productionCost1.calculate().calculateFrameworkCostOfAllPieces(element1));
        Assertions.assertEquals(11500, productionCost2.calculate().calculateFrameworkCostOfAllPieces(element2));
        Assertions.assertEquals(7200, productionCost3.calculate().calculateFrameworkCostOfAllPieces(element3));
    }

    @Test
    void shouldCalculateEnergyAndLabourCostsOfOnePiece() {
        Assertions.assertEquals(150, productionCost1.calculate().calculateEnergyAndLabourCostsOfOnePiece(element1));
        Assertions.assertEquals(1300, productionCost2.calculate().calculateEnergyAndLabourCostsOfOnePiece(element2));
        Assertions.assertEquals(570, productionCost3.calculate().calculateEnergyAndLabourCostsOfOnePiece(element3));
    }

    @Test
    void shouldCalculateEnergyAndLabourCostsOfAllPieces() {
        Assertions.assertEquals(600, productionCost1.calculate().calculateEnergyAndLabourCostsOfAllPieces(element1));
        Assertions.assertEquals(6500, productionCost2.calculate().calculateEnergyAndLabourCostsOfAllPieces(element2));
        Assertions.assertEquals(3420, productionCost3.calculate().calculateEnergyAndLabourCostsOfAllPieces(element3));
    }

    @Test
    void shouldCalculateAccessoryCostOfOnePiece() {
        Assertions.assertEquals(0, productionCost1.calculate().calculateAccessoryCostOfOnePiece(element1));
        Assertions.assertEquals(60, productionCost2.calculate().calculateAccessoryCostOfOnePiece(element2));
        Assertions.assertEquals(255, productionCost3.calculate().calculateAccessoryCostOfOnePiece(element3));
    }

    @Test
    void shouldCalculateAccessoryCostOfAllPieces() {
        Assertions.assertEquals(0, productionCost1.calculate().calculateAccessoryCostOfAllPieces(element1));
        Assertions.assertEquals(300, productionCost2.calculate().calculateAccessoryCostOfAllPieces(element2));
        Assertions.assertEquals(1530, productionCost3.calculate().calculateAccessoryCostOfAllPieces(element3));

    }

    @Test
    void ShouldCalculateAllProductionCostOfOnePiece() {
        Assertions.assertEquals(1390, productionCost1.calculate().calculateAllProductionCostOfOnePiece(element1));
        Assertions.assertEquals(12321.25, productionCost2.calculate().calculateAllProductionCostOfOnePiece(element2));
        Assertions.assertEquals(4515, productionCost3.calculate().calculateAllProductionCostOfOnePiece(element3));
    }

    @Test
    void ShouldCalculateAllProductionCostOfAllPieces() {
        Assertions.assertEquals(5560, productionCost1.calculate().calculateAllProductionCostOfAllPieces(element1));
        Assertions.assertEquals(61606.25, productionCost2.calculate().calculateAllProductionCostOfAllPieces(element2));
        Assertions.assertEquals(27090, productionCost3.calculate().calculateAllProductionCostOfAllPieces(element3));
    }
}