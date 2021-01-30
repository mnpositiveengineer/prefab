package com.prefab.sales.production;

import com.prefab.sales.production.interfaces.CalculateElement;

public class CustomizedElement extends StandardElement {

    private float volume;
    private float area;
    private float weight;
    private float frameworkArea;
    private float steelWeight;
    private float tensionWeight;
    private float volumeOfConsoles;
    private float volumeOfCutouts;

    public CustomizedElement(String name, int amount, String typeOfElement) {
        super(name, amount, typeOfElement);
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getFrameworkArea() {
        return frameworkArea;
    }

    public void setFrameworkArea(float frameworkArea) {
        this.frameworkArea = frameworkArea;
    }

    public float getSteelWeight() {
        return steelWeight;
    }

    public void setSteelWeight(float steelWeight) {
        this.steelWeight = steelWeight;
    }

    public float getTensionWeight() {
        return tensionWeight;
    }

    public void setTensionWeight(float tensionWeight) {
        this.tensionWeight = tensionWeight;
    }

    public float getVolumeOfConsoles() {
        return volumeOfConsoles;
    }

    public void setVolumeOfConsoles(float volumeOfConsoles) {
        this.volumeOfConsoles = volumeOfConsoles;
    }

    public float getVolumeOfCutouts() {
        return volumeOfCutouts;
    }

    public void setVolumeOfCutouts(float volumeOfCutouts) {
        this.volumeOfCutouts = volumeOfCutouts;
    }

    public CalculateElement calculate(){
        return new CustomizedCalculateElementCalculator(this);
    }

    public class CustomizedCalculateElementCalculator extends Calculator {

        private CustomizedElement element;

        public CustomizedCalculateElementCalculator(CustomizedElement element) {
            super(element);
            this.element = element;
        }

        @Override
        public float calculateVolume() {
            return element.getVolume() + element.getVolumeOfConsoles() - element.getVolumeOfCutouts();
        }

        @Override
        public float calculateArea() {
            return element.getArea();
        }

        @Override
        public float calculateWeight() {
            return element.getWeight();
        }

        @Override
        public float calculateSteelWeight() {
            return element.getSteelWeight();
        }

        @Override
        public float calculateTensionWeight() {
            return element.getTensionWeight();
        }

        @Override
        public float calculateFrameworkArea() {
            return element.getFrameworkArea();
        }
    }


}
