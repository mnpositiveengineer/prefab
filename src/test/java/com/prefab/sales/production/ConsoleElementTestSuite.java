package com.prefab.sales.production;

import com.prefab.sales.utils.enums.ConsoleOrCutoutType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleElementTestSuite {

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
    void ShouldAddConsoleAndCutout() {
        byte numberOfConsoles = (byte)(element.getConsolesAndCutouts().size());
        Assertions.assertEquals(4, numberOfConsoles);
    }

    @Test
    void ShouldRemoveConsoleAndCutout() {
        element.removeConsoleOrCutout(console1, cutout1);
        byte numberOfConsoles = (byte)(element.getConsolesAndCutouts().size());
        Assertions.assertEquals(2, numberOfConsoles);
    }

}