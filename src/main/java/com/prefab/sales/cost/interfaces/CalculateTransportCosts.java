package com.prefab.sales.cost.interfaces;

public interface CalculateTransportCosts extends CostInterface {
    float calculateTotalWeightOfElementsInGroup();
    short calculateNumberOfCTransportsInGroup();
    double calculateCostOfTransportInGroup();
}
