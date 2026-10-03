package com.foodwastemanagement.services;

import com.foodwastemanagement.models.QuantityUnit;

import java.util.List;

public interface QuantityUnitService {
    QuantityUnit createQuantityUnit();
    QuantityUnit updateQuantityUnit();
    QuantityUnit deleteQuantityUnit();
    List<QuantityUnit> getListOfQuantityUnits();
}
