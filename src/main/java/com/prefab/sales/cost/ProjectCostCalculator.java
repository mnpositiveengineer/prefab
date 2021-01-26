package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.cost.interfaces.*;
import com.prefab.sales.production.interfaces.CreateElement;

import java.util.*;

public class ProjectCostCalculator implements CalculateProjectCosts {

    private Project project;

    public ProjectCostCalculator(Project project) {
        this.project = project;
    }

    @Override
    public double calculateAllProductionCostsOfProject () {

        if (project.getElements().isEmpty() || project.getProductionGroups().isEmpty())
            return 0;
        double cost = 0;
        for (Map.Entry<CreateCostGroups, Set<CreateElement>> group : project.getProductionGroups().entrySet()) {
            CalculateProductionCosts calculator = (CalculateProductionCosts)group.getKey().calculate();
                for (CreateElement element : group.getValue()) {
                    cost = cost + calculator.calculateAllProductionCostOfAllPieces(element);
                }
        }
        return cost;
    }

    @Override
    public double calculateAllTransportCostsOfProject () {
        if (project.getElements().isEmpty() || project.getTransportGroups().isEmpty())
            return 0;
        double cost = 0;
        for (Map.Entry<CreateCostGroups, Set<CreateElement>> group : project.getTransportGroups().entrySet()) {
            CalculateTransportCosts calculator = (CalculateTransportCosts) group.getKey().calculate();
            cost = cost + calculator.calculateCostOfTransportInGroup();
        }
        return cost;
    }

    @Override
    public int calculateAllAssemblyCostsOfProject () {
        if (project.getElements().isEmpty() || project.getAssemblyGroups().isEmpty())
            return 0;
        int cost = 0;
        for (Map.Entry<CreateCostGroups, Set<CreateElement>> group : project.getAssemblyGroups().entrySet()) {
            CalculateAssemblyCost calculator = (CalculateAssemblyCost) group.getKey().calculate();
            cost = cost + calculator.calculateCostOfAssemblyInGroup();
        }
        return cost;
    }

//    private void checkIfAllElementsAreAssignedToTransport() {
//        List<Object> listOfAllElements = project.getElements();
//        List<Object> listOfTransportElements = new LinkedList<>();
//        for (Map.Entry<Object, List<Object>> map : project.getTransportGroups().entrySet()){
//            for(Object object : map.getValue()){
//                listOfTransportElements.add(object);
//            }
//        }
//        for (Object object : listOfAllElements) {
//            if(!listOfTransportElements.contains(object))
//                throw new IllegalArgumentException(object.toString() + " is not assigned to any transport.");
//        }
//    }
}
