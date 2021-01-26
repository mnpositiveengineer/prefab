package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.cost.interfaces.CalculateTransportCosts;

public class Transport extends CostGroupsCreator {

    private short distance;
    private short costOfOneTransport;
    private String typeOfCar;
    private short maxCarLoadInTones = 21;

    public Transport(Project project, int costOfOneTransport) {
        super(project, project.getTransportGroups());
        this.costOfOneTransport = (short)costOfOneTransport;
    }

    public void setMaxCarLoadInTones(int maxCarLoadInTones) {
        this.maxCarLoadInTones = (short)maxCarLoadInTones;
    }

    public void setDistance(short distance) {
        this.distance = distance;
    }

    public void setCostOfOneTransport(short costOfOneTransport) {
        this.costOfOneTransport = costOfOneTransport;
    }

    public void setTypeOfCar(String typeOfCar) {
        this.typeOfCar = typeOfCar;
    }

    @Override
    public CalculateTransportCalculator calculate(){
        return new CalculateTransportCalculator(this);
    }

    public class CalculateTransportCalculator implements CalculateTransportCosts {

        private Transport transport;

        public CalculateTransportCalculator(Transport transport) {
            this.transport = transport;
        }

        @Override
        public float calculateTotalWeightOfElementsInGroup() {
            return (float)transport.getElementsInGroup().stream()
                    .mapToDouble(m -> m.calculate().calculateWeightTotal())
                    .reduce(0, Double::sum);
        }

        @Override
        public short calculateNumberOfCTransportsInGroup() {
            return (short)(Math.ceil(calculateTotalWeightOfElementsInGroup()/maxCarLoadInTones));
        }

        @Override
        public double calculateCostOfTransportInGroup() {
            return calculateNumberOfCTransportsInGroup() * costOfOneTransport;
        }
    }

}
