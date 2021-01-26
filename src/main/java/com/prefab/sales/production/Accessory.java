package com.prefab.sales.production;

import com.prefab.sales.production.interfaces.CreateAccessory;
import com.prefab.sales.utils.enums.Unit;
import com.prefab.sales.utils.Validation;

public class Accessory implements CreateAccessory {
    private String name;
    private Unit unit;
    private float unitPrice;

    public Accessory(String name, Unit unit) {
        setName(name);
        this.unit = unit;
    }

    private void setName(String name) {
        Validation.alphanumericValue(name);
        this.name = name.trim();
    }

    public void setUnitPrice(float unitPrice) {
        Validation.greaterOrEqualZero(unitPrice);
        this.unitPrice = unitPrice;
    }

    @Override
    public float getUnitPrice() {
        return unitPrice;
    }

    public String getName() {
        return name;
    }
}
