package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.cost.interfaces.CreateCostGroups;
import com.prefab.sales.production.interfaces.CreateElement;
import com.prefab.sales.utils.CollectionModification;
import com.prefab.sales.utils.exceptions.ObjectAlreadyAddedException;
import com.prefab.sales.utils.exceptions.ObjectNotInCollectionException;

import java.util.*;

public abstract class CostGroupsCreator implements CreateCostGroups {

    private Project project;
    private Set<CreateElement> elementsInGroup = new HashSet<>();
    private Map<CreateCostGroups, Set<CreateElement>> map = new HashMap<>();

    public CostGroupsCreator(Project project, Map<CreateCostGroups, Set<CreateElement>> map) {
        this.project = project;
        this.map = map;
    }

    @Override
    public void addAllElementsToGroup() {
        CollectionModification.addAllElementsToGroup(map, this,
                project.getElements(), elementsInGroup);
    }

    @Override
    public void removeAllElementsFromGroup() {
        CollectionModification.removeAllElementsFromGroup(map, this);
    }

    @Override
    public void addSelectedElementsToGroup(CreateElement...elements) {
        for (CreateElement element : elements) {
            try {
                CollectionModification.addSelectedElementsToGroup(map, this,
                        element, elementsInGroup);
            } catch (ObjectAlreadyAddedException e) {
                System.out.println(element.toString() + " is already assigned to transport.");
            }
        }
    }

    @Override
    public void removeSelectedElementsFromGroup(CreateElement...elements) {
        for (CreateElement element : elements) {
            try {
                CollectionModification.removeSelectedElementsFromGroup(map, this,
                        element, elementsInGroup);
            } catch (ObjectNotInCollectionException e) {
                System.out.println(element.toString() + " is not on the list.");
            }
        }
    }

    @Override
    public Set<CreateElement> getElementsInGroup() {
        return elementsInGroup;
    }
}
