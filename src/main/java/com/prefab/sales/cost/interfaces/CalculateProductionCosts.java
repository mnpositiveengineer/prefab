package com.prefab.sales.cost.interfaces;

import com.prefab.sales.production.interfaces.CreateElement;

public interface CalculateProductionCosts extends CostInterface {
    float calculateConcreteCostOfOnePiece(CreateElement element);
    float calculateConcreteCostOfAllPieces(CreateElement element);
    float calculateSteelCostOfOnePiece(CreateElement element);
    float calculateSteelCostOfAllPieces(CreateElement element);
    float calculateTensionCostOfOnePiece(CreateElement element);
    float calculateTensionCostOfOAllPieces(CreateElement element);
    float calculateFrameworkCostOfOnePiece(CreateElement element);
    float calculateFrameworkCostOfAllPieces(CreateElement element);
    float calculateEnergyAndLabourCostsOfOnePiece(CreateElement element);
    float calculateEnergyAndLabourCostsOfAllPieces(CreateElement element);
    float calculateAccessoryCostOfOnePiece(CreateElement element);
    float calculateAccessoryCostOfAllPieces(CreateElement element);
    float calculateAllProductionCostOfOnePiece(CreateElement element);
    float calculateAllProductionCostOfAllPieces(CreateElement element);
}
