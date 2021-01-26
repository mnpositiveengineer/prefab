package com.prefab.sales.production;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomizedElementCalculatorTestSuite {

    CustomizedElement element = new CustomizedElement("Customized Element", 7, "Deltabeam");

    @BeforeEach
    void prepareElement() {
        element.setVolume(4);
        element.setArea(18);
        element.setWeight(12);
        element.setSteelWeight(600);
        element.setTensionWeight(300);
        element.setFrameworkArea(20);
        element.setVolumeOfConsoles(1);
        element.setVolumeOfCutouts(0.5F);
    }

    @Test
    void shouldCalculateTotalVolume() {
        Assertions.assertEquals(31.5, element.calculate().calculateVolumeTotal());
    }

    @Test
    void shouldCalculateTotalArea() {
        Assertions.assertEquals(126, element.calculate().calculateAreaTotal());
    }

    @Test
    void shouldCalculateWeightTotal() {
        Assertions.assertEquals(84, element.calculate().calculateWeightTotal());
    }

    @Test
    void shouldCalculateSteelWeightTotal() {
        Assertions.assertEquals(4200, element.calculate().calculateSteelWeightTotal());
    }

    @Test
    void shouldCalculateTensionWeightTotal() {
        Assertions.assertEquals(2100, element.calculate().calculateTensionWeightTotal());
    }

    @Test
    void shouldCalculateFrameworkAreaTotal() {
        Assertions.assertEquals(140, element.calculate().calculateFrameworkAreaTotal());
    }
}