package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.client.Prospect;
import com.prefab.sales.production.ConsoleElement;
import com.prefab.sales.production.StandardElement;
import com.prefab.sales.production.CustomizedElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssemblyTestSuite {

    Prospect prospect = new Prospect("Testing Company");
    Project project = new Project("Testing Project", prospect);

    StandardElement element1 = new StandardElement("Element 1", 4, "Slab");
    ConsoleElement element2 = new ConsoleElement("Element 2", 5, "Slab");
    CustomizedElement element3 = new CustomizedElement("Element 3", 6, "Beam");

    Assembly assembly1 = new Assembly(project, 1000);
    Assembly assembly2 = new Assembly(project, 2000);

    @BeforeEach
    void prepareTestData(){
        project.addElement(element1, element2, element3);
        assembly1.addSelectedElementsToGroup(element1, element2);
        assembly2.addSelectedElementsToGroup(element3);
    }

    @Test
    void ShouldCalculateAssemblyCostOfGroups() {
        Assertions.assertEquals(2000, assembly1.calculate().calculateCostOfAssemblyInGroup());
        Assertions.assertEquals(2000, assembly2.calculate().calculateCostOfAssemblyInGroup());
    }

    @Test
    void ShouldCalculateAssemblyCostOfGroupsAfterPriceIsChanged() {
        assembly1.setAssemblyCost(500);
        assembly2.setAssemblyCost(600);
        Assertions.assertEquals(1000, assembly1.calculate().calculateCostOfAssemblyInGroup());
        Assertions.assertEquals(600, assembly2.calculate().calculateCostOfAssemblyInGroup());
    }

}