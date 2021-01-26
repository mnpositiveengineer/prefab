package com.prefab.sales.cost.interfaces;

import com.prefab.sales.production.interfaces.CreateElement;
import java.util.Set;

public interface CreateCostGroups {
    void addAllElementsToGroup();
    void removeAllElementsFromGroup();
    void addSelectedElementsToGroup(CreateElement...elements);
    void removeSelectedElementsFromGroup(CreateElement...elements);
    Set<CreateElement> getElementsInGroup();
    CostInterface calculate();
}
