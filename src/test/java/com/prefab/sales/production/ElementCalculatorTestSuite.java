package com.prefab.sales.production;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementCalculatorTestSuite {

    StandardElement element = new StandardElement("S1", 10, "Column");

    @BeforeEach
    void prepareElement() {
        element.setHeight(0.5F);
        element.setWidth(0.4F);
        element.setLength(6.4F);
        element.setSteelSaturation(100);
        element.setTensionSaturation(50);
    }

    @Test
    void ShouldCalculateTotalVolume() {
        float actualResult = element.calculate().calculateVolumeTotal();
        assertEquals(12.8F, actualResult, 0.001);
    }

    @Test
    void ShouldCalculateTotalArea() {
        float actualResult = element.calculate().calculateAreaTotal();
        Assertions.assertEquals(25.6F, actualResult, 0.001);
    }

    @Test
    void ShouldCalculateTotalWeight() {
        float actualResult = element.calculate().calculateWeightTotal();
        Assertions.assertEquals(32F, actualResult, 0.001);
    }

    @Test
    void ShouldCalculateTotalSteel() {
        float actualResult = element.calculate().calculateSteelWeightTotal();
        Assertions.assertEquals(1280F, actualResult, 0.001);
    }

    @Test
    void ShouldCalculateTotalTension() {
        float actualResult = element.calculate().calculateTensionWeightTotal();
        Assertions.assertEquals(640F, actualResult, 0.001);
    }

    @Test
    void ShouldCalculateTotalFrameworkArea() {
        float actualResult = element.calculate().calculateFrameworkAreaTotal();
        Assertions.assertEquals(93.6F, actualResult, 0.001);
    }

}