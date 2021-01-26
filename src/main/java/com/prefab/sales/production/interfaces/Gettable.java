package com.prefab.sales.production.interfaces;

import java.util.Map;
import java.util.Set;

public interface Gettable {
    Map<CreateAccessory, Set<Float>> getAccessories();
    String getName();
    short getAmount();
}
