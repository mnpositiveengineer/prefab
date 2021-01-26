package com.prefab.sales.production;

import com.prefab.sales.utils.enums.Unit;
import com.prefab.sales.utils.exceptions.IncorrectDateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ElementTestSuite {

    StandardElement element = new StandardElement("S1", 10, "Column");
    Accessory bindax = new Accessory("Bindax", Unit.m);
    Accessory pipe = new Accessory("Pipe", Unit.m);
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    void ShouldAddAccessoriesToElement() {
        element.addAccessory(bindax, 0.8F);
        element.addAccessory(bindax, 1.8F);
        element.addAccessory(pipe, 3.2F);
        Assertions.assertEquals(2, element.getAccessories().size());
        Assertions.assertEquals(2, element.getAccessories().get(bindax).size());
        Assertions.assertEquals(1, element.getAccessories().get(pipe).size());
    }

    @Test
    void ShouldRemoveAccessoryFromElement() {
        element.addAccessory(bindax, 0.8F);
        element.addAccessory(bindax, 1.8F);
        element.addAccessory(pipe, 3.2F);
        element.removeAccessory(pipe);
        Assertions.assertEquals(1, element.getAccessories().size());
        Assertions.assertEquals(2, element.getAccessories().get(bindax).size());
        element.removeAccessory(bindax);
        Assertions.assertEquals(0, element.getAccessories().size());
    }

    @Test
    void ShouldThrowWarningMessageThatAccessoryIsNotAddedToElement() {
        System.setOut(new PrintStream(outputStreamCaptor));
        element.removeAccessory(pipe);
        Assertions.assertEquals("Pipe is not added to element.", outputStreamCaptor.toString()
                .trim());
        element.addAccessory(pipe, 3.2F);
        outputStreamCaptor.reset();
        element.removeAccessory(bindax);
        Assertions.assertEquals("Bindax is not added to element.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void ShouldRemoveValueFromAccessory(){
        element.addAccessory(bindax, 0.8F);
        element.addAccessory(bindax, 1.8F);
        element.addAccessory(pipe, 3.2F);
        element.removeValueFromAccessory(bindax, 0.8F);
        Assertions.assertEquals(2, element.getAccessories().size());
        Assertions.assertEquals(1, element.getAccessories().get(bindax).size());
        Assertions.assertEquals(1, element.getAccessories().get(pipe).size());
        element.removeValueFromAccessory(pipe, 3.2F);
        Assertions.assertEquals(1, element.getAccessories().size());
        Assertions.assertEquals(1, element.getAccessories().get(bindax).size());
    }

    @Test
    void ShouldThrowWarningMessageThatValueIsNotAdded(){
        System.setOut(new PrintStream(outputStreamCaptor));
        element.addAccessory(bindax, 0.8F);
        element.removeValueFromAccessory(bindax, 0.9F);
        Assertions.assertEquals("Bindax has no value 0.9.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void ShouldNotThrowExceptionWhenSettingAssemblyDates() throws IncorrectDateException {
        element.setAssemblyStart(LocalDate.of(2021, 1, 1));
        element.setAssemblyEnd(LocalDate.of(2021, 1, 1));
        element.setAssemblyStart(LocalDate.of(2020, 12, 31));
        element.setAssemblyEnd(LocalDate.of(2021, 1, 2));
        element.setAssemblyStart(LocalDate.of(2021, 1, 2));
    }

    @Test
    void ShouldThrowExceptionWhenSettingAssemblyDates() throws IncorrectDateException {
        element.setAssemblyStart(LocalDate.of(2021, 1, 2));
        Exception exception =
                assertThrows(IncorrectDateException.class, ()-> element.setAssemblyEnd(LocalDate.of(2021, 1, 1)));
        Assertions.assertEquals("Assembly end cannot be before Assembly start.", exception.getMessage());
        element.setAssemblyEnd(LocalDate.of(2021, 1, 2));
        exception =
                assertThrows(IncorrectDateException.class, ()-> element.setAssemblyStart(LocalDate.of(2021, 1, 3)));
        Assertions.assertEquals("Assembly start cannot be after Assembly end.", exception.getMessage());
    }
}