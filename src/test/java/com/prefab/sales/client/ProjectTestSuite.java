package com.prefab.sales.client;

import com.prefab.sales.production.StandardElement;
import com.prefab.sales.production.ConsoleElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ProjectTestSuite {

    Project project = new Project("Project 1", new Prospect("Testing Company"));
    StandardElement column = new ConsoleElement("C1", 4, "Column");
    StandardElement beam = new StandardElement("B1", 2, "Beam");
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void prepareData() {
        project.addElement(column, beam);
    }

    @Test
    void ShouldAddElementToProject() {
        Assertions.assertEquals(2, project.getElements().size());
    }

    @Test
    void ShouldThrowWarningMessageThatElementAlreadyExists() {
        System.setOut(new PrintStream(outputStreamCaptor));
        project.addElement(column);
        Assertions.assertEquals("Element C1 already exists.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();
        project.addElement(beam);
        Assertions.assertEquals("Element B1 already exists.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void ShouldRemoveElementFromProject() {
        project.removeElement(column);
        Assertions.assertEquals(1, project.getElements().size());
        project.removeElement(beam);
        Assertions.assertEquals(0, project.getElements().size());
    }

    @Test
    void ShouldThrowWarningMessageThatElementDoesNotExist() {
        project.removeElement(column, beam);
        System.setOut(new PrintStream(outputStreamCaptor));
        project.removeElement(column);
        Assertions.assertEquals("Element C1 is not on the list.", outputStreamCaptor.toString()
                .trim());
        outputStreamCaptor.reset();
        project.removeElement(beam);
        Assertions.assertEquals("Element B1 is not on the list.", outputStreamCaptor.toString()
                .trim());
    }


}