package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.cost.interfaces.CalculateProductionCosts;
import com.prefab.sales.production.interfaces.CreateElement;
import com.prefab.sales.utils.Validation;

public class Production extends CostGroupsCreator {

    private short concreteCost;
    private float steelCost;
    private float tensionCost;
    private short frameworkCost;
    private short manHourCost;
    private short energyCost;
    private short facultyCost;

    public Production(Project project,
                      int concreteCost,
                      float steelCost,
                      float tensionCost,
                      int frameworkCost,
                      int manHourCost,
                      int energyCost,
                      int facultyCost)
    {
        super(project, project.getProductionGroups());
        setConcreteCost((short)concreteCost);
        setSteelCost(steelCost);
        setTensionCost(tensionCost);
        setFrameworkCost((short)frameworkCost);
        setManHourCost((short)manHourCost);
        setEnergyCost((short)energyCost);
        setFacultyCost((short)facultyCost);
    }

    public void setConcreteCost(short concreteCost) {
        Validation.greaterOrEqualZero(concreteCost);
        this.concreteCost = concreteCost;
    }

    public void setSteelCost(float steelCost) {
        Validation.greaterOrEqualZero(steelCost);
        this.steelCost = steelCost;
    }

    public void setTensionCost(float tensionCost) {
        Validation.greaterOrEqualZero(tensionCost);
        this.tensionCost = tensionCost;
    }

    public void setFrameworkCost(short frameworkCost) {
        Validation.greaterOrEqualZero(frameworkCost);
        this.frameworkCost = frameworkCost;
    }

    public void setManHourCost(short manHourCost) {
        Validation.greaterOrEqualZero(manHourCost);
        this.manHourCost = manHourCost;
    }

    public void setEnergyCost(short energyCost) {
        Validation.greaterOrEqualZero(energyCost);
        this.energyCost = energyCost;
    }

    public void setFacultyCost(short facultyCost) {
        Validation.greaterOrEqualZero(facultyCost);
        this.facultyCost = facultyCost;
    }

    public short getConcreteCost() {
        return concreteCost;
    }

    public float getSteelCost() {
        return steelCost;
    }

    public float getTensionCost() {
        return tensionCost;
    }

    public short getFrameworkCost() {
        return frameworkCost;
    }

    public short getManHourCost() {
        return manHourCost;
    }

    public short getEnergyCost() {
        return energyCost;
    }

    public short getFacultyCost() {
        return facultyCost;
    }

    @Override
    public CalculateProductionCostCalculator calculate() {
        return new CalculateProductionCostCalculator(this);
    }

    public class CalculateProductionCostCalculator implements CalculateProductionCosts {

        private Production production;

        public CalculateProductionCostCalculator(Production production) {
            this.production = production;
        }

        @Override
        public float calculateConcreteCostOfOnePiece(CreateElement element) {
            return concreteCost * element.calculate().calculateVolume();
        }

        @Override
        public float calculateConcreteCostOfAllPieces(CreateElement element) {
            return calculateConcreteCostOfOnePiece(element) * element.getAmount();
        }

        @Override
        public float calculateSteelCostOfOnePiece(CreateElement element) {
            return steelCost * element.calculate().calculateSteelWeight();
        }

        @Override
        public float calculateSteelCostOfAllPieces(CreateElement element) {
            return calculateSteelCostOfOnePiece(element) * element.getAmount();
        }

        @Override
        public float calculateTensionCostOfOnePiece(CreateElement element) {
            return tensionCost * element.calculate().calculateTensionWeight();
        }

        @Override
        public float calculateTensionCostOfOAllPieces(CreateElement element) {
            return calculateTensionCostOfOnePiece(element) * element.getAmount();
        }

        @Override
        public float calculateFrameworkCostOfOnePiece(CreateElement element) {
            return frameworkCost * element.calculate().calculateFrameworkArea();
        }

        @Override
        public float calculateFrameworkCostOfAllPieces(CreateElement element) {
            return calculateFrameworkCostOfOnePiece(element) * element.getAmount();
        }

        @Override
        public float calculateEnergyAndLabourCostsOfOnePiece(CreateElement element) {
            return (manHourCost + facultyCost + energyCost)
                    * element.calculate().calculateVolume();
        }

        @Override
        public float calculateEnergyAndLabourCostsOfAllPieces(CreateElement element) {
            return calculateEnergyAndLabourCostsOfOnePiece(element) * element.getAmount();
        }

        @Override
        public float calculateAccessoryCostOfOnePiece(CreateElement element) {
            return (float)element.getAccessories().entrySet().stream()
                    .map(m->m.getKey().getUnitPrice()
                            * m.getValue().stream()
                            .mapToDouble(n->n.doubleValue())
                            .sum())
                    .mapToDouble(m->m.floatValue())
                    .sum();
        }

        @Override
        public float calculateAccessoryCostOfAllPieces(CreateElement element) {
            return calculateAccessoryCostOfOnePiece(element) * element.getAmount();
        }

        @Override
        public float calculateAllProductionCostOfOnePiece(CreateElement element) {
            return calculateConcreteCostOfOnePiece(element)
                    + calculateSteelCostOfOnePiece(element)
                    + calculateTensionCostOfOnePiece(element)
                    + calculateFrameworkCostOfOnePiece(element)
                    + calculateEnergyAndLabourCostsOfOnePiece(element)
                    + calculateAccessoryCostOfOnePiece(element);
        }

        @Override
        public float calculateAllProductionCostOfAllPieces(CreateElement element) {
            return calculateAllProductionCostOfOnePiece(element) * element.getAmount();
        }
    }
}
