package com.prefab.sales.production;

import com.prefab.sales.production.interfaces.CalculateElement;
import com.prefab.sales.production.interfaces.CreateAccessory;
import com.prefab.sales.utils.CollectionModification;
import com.prefab.sales.utils.Validation;
import com.prefab.sales.utils.exceptions.IncorrectDateException;
import com.prefab.sales.utils.exceptions.ObjectNotInCollectionException;

import java.time.LocalDate;
import java.util.*;

public class StandardElement implements com.prefab.sales.production.interfaces.CreateElement {

    private String name;
    private short amount;
    private float height;
    private float width;
    private float length;
    private short steelSaturation;
    private short tensionSaturation;
    private LocalDate assemblyStart;
    private LocalDate assemblyEnd;
    private String typeOfElement;
    private Map<CreateAccessory, Set<Float>> accessories = new HashMap<>();

    public StandardElement(String name, int amount, String typeOfElement) {
        setName(name);
        setAmount((short)amount);
        setTypeOfElement(typeOfElement);
    }

    public void addAccessory(Accessory accessory, Float amount) {
        Validation.greaterOrEqualZero(amount);
        CollectionModification.addToMap(accessories, accessory, amount);
    }

    public void removeAccessory(Accessory accessory) {
        try {
            CollectionModification.removeKeyFromMap(accessories, accessory);
        } catch (ObjectNotInCollectionException e) {
            System.out.println(accessory.getName() + " is not added to element.");
        }
    }

    public void removeValueFromAccessory(Accessory accessory, Float value) {
        try {
            CollectionModification.removeValueFromMap(accessories, accessory, value);
        } catch (ObjectNotInCollectionException e) {
            System.out.println(accessory.getName() + " has no value " + value.toString() + ".");
        }
    }

    public void setName(String name) {
        Validation.alphanumericValue(name);
        this.name = name.trim();
    }

    public void setAmount(short amount) {
        Validation.greaterOrEqualZero(amount);
        this.amount = amount;
    }

    public void setHeight(float height) {
        Validation.greaterOrEqualZero(height);
        this.height = height;
    }

    public void setWidth(float width) {
        Validation.greaterOrEqualZero(width);
        this.width = width;
    }

    public void setLength(float length) {
        Validation.greaterOrEqualZero(length);
        this.length = length;
    }

    public void setSteelSaturation(int steelSaturation) {
        Validation.greaterOrEqualZero(steelSaturation);
        this.steelSaturation = (short)steelSaturation;
    }

    public void setTensionSaturation(int tensionSaturation) {
        Validation.greaterOrEqualZero(tensionSaturation);
        this.tensionSaturation = (short)tensionSaturation;
    }

    public void setAssemblyStart(LocalDate assemblyStart) throws IncorrectDateException {
        if (assemblyEnd == null
                || this.assemblyEnd.isAfter(assemblyStart)
                || this.assemblyEnd.isEqual(assemblyStart)) {
            this.assemblyStart = assemblyStart;
            return;
        }
        throw new IncorrectDateException("Assembly start cannot be after Assembly end.");
    }

    public void setAssemblyEnd(LocalDate assemblyEnd) throws IncorrectDateException {
        if (assemblyStart == null
                || this.assemblyStart.isBefore(assemblyEnd)
                || this.assemblyStart.isEqual(assemblyEnd)) {
            this.assemblyEnd = assemblyEnd;
            return;
        }
        throw new IncorrectDateException("Assembly end cannot be before Assembly start.");
    }

    public void setTypeOfElement(String typeOfElement) {
        Validation.alphanumericValue(typeOfElement);
        this.typeOfElement = typeOfElement.trim();
    }

    @Override
    public String getName() {
        return name;
    }

    public short getAmount() {
        return amount;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getLength() {
        return length;
    }

    public short getSteelSaturation() {
        return steelSaturation;
    }

    public short getTensionSaturation() {
        return tensionSaturation;
    }

    public LocalDate getAssemblyStart() {
        return assemblyStart;
    }

    public LocalDate getAssemblyEnd() {
        return assemblyEnd;
    }

    public String getTypeOfElement() {
        return typeOfElement;
    }

    @Override
    public Map<CreateAccessory, Set<Float>> getAccessories() {
        return accessories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardElement element = (StandardElement) o;
        return Objects.equals(name, element.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public CalculateElement calculate() {
        return new Calculator(this);
    }

    public class Calculator implements CalculateElement {

        private StandardElement element;
        private final float CONCRETE_WEIGHT_IN_TONES = 2.5F;

        public Calculator(StandardElement element) {
            this.element = element;
        }

        public float calculateVolume() {
            return height * width * length;
        }

        public float calculateVolumeTotal() {
            return calculateVolume() * amount;
        }

        public float calculateArea() {
            return width * length;
        }

        public float calculateAreaTotal() {
            return calculateArea() * amount;
        }

        public float calculateWeight() {
            return calculateVolume() * CONCRETE_WEIGHT_IN_TONES;
        }

        public float calculateWeightTotal() {
            return calculateWeight() * amount;
        }

        public float calculateSteelWeight() {
            return calculateVolume() * steelSaturation;
        }

        public float calculateSteelWeightTotal() {
            return calculateSteelWeight() * amount;
        }

        public float calculateTensionWeight() {
            return calculateVolume() * tensionSaturation;
        }

        public float calculateTensionWeightTotal() {
            return calculateTensionWeight() * amount;
        }

        public float calculateFrameworkArea() {
            return calculateArea() + height * 2 * (width + length);
        }

        public float calculateFrameworkAreaTotal() {
            return calculateFrameworkArea() * amount;
        }
    }

}
