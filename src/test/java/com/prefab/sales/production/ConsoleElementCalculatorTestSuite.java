package com.prefab.sales.production;

import com.prefab.sales.utils.enums.ConsoleOrCutoutType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleElementCalculatorTestSuite {

    ConsoleElement element = new ConsoleElement("Column", 5, "Column");
    ConsoleElement.ConsoleCreator console1 = element.createConsole(ConsoleOrCutoutType.console, 2, 0.5F, 0.5F, 0.4F);
    ConsoleElement.ConsoleCreator console2 = element.createConsole(ConsoleOrCutoutType.console, 2, 0.4F, 0.4F, 0.3F);
    ConsoleElement.ConsoleCreator cutout1 = element.createConsole(ConsoleOrCutoutType.cutout, 2, 0.2F, 0.2F, 0.2F);
    ConsoleElement.ConsoleCreator cutout2 = element.createConsole(ConsoleOrCutoutType.cutout, 2, 0.1F, 0.1F, 0.1F);

    @BeforeEach
    void addConsolesAndCutouts() {
        element.setHeight(0.6F);
        element.setWidth(0.6F);
        element.setLength(6);
        element.addConsoleOrCutout(console1, console2, cutout1, cutout2);
    }

    @Test
    void ShouldCalculateVolumeOfElement() {
        float actualResult = element.calculate().calculateVolume();
        Assertions.assertEquals(2.438, actualResult, 0.001);
        element.removeConsoleOrCutout(console1);
        actualResult = element.calculate().calculateVolume();
        Assertions.assertEquals(2.238, actualResult, 0.001);
        element.removeConsoleOrCutout(cutout2);
        actualResult = element.calculate().calculateVolume();
        Assertions.assertEquals(2.24, actualResult, 0.001);
        element.removeConsoleOrCutout(console2, cutout1);
        actualResult = element.calculate().calculateVolume();
        Assertions.assertEquals(2.16, actualResult, 0.001);
    }

    @Test
    void ShouldCalculateTotalVolumeOfElement() {
        float actualResult = element.calculate().calculateVolumeTotal();
        Assertions.assertEquals(12.19, actualResult, 0.001);
    }

    @Test
    void ShouldCalculateFrameworkOfElement() {
        float actualResult = element.calculate().calculateFrameworkArea();
        Assertions.assertEquals(14.48, actualResult, 0.001);
        element.removeConsoleOrCutout(console1);
        actualResult = element.calculate().calculateFrameworkArea();
        Assertions.assertEquals(12.88, actualResult, 0.001);
        element.removeConsoleOrCutout(cutout2);
        actualResult = element.calculate().calculateFrameworkArea();
        Assertions.assertEquals(12.8, actualResult, 0.001);
        element.removeConsoleOrCutout(console2, cutout1);
        actualResult = element.calculate().calculateFrameworkArea();
        Assertions.assertEquals(11.52, actualResult, 0.001);
    }

    @Test
    void ShouldCalculateTotalFrameworkOfElement() {
        float actualResult = element.calculate().calculateFrameworkAreaTotal();
        Assertions.assertEquals(72.4, actualResult, 0.001);
    }

}