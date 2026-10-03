package com.foodwastemanagement.services.implementations;

import com.foodwastemanagement.exceptions.customExceptions.ResourceNotFoundException;
import com.foodwastemanagement.models.QuantityUnit;
import com.foodwastemanagement.repositories.QuantityUnitRepository;
import com.foodwastemanagement.services.QuantityUnitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityUnitServiceImplementation implements QuantityUnitService {

    private final QuantityUnitRepository quantityUnitRepository;

    public QuantityUnitServiceImplementation(QuantityUnitRepository quantityUnitRepository) {
        this.quantityUnitRepository = quantityUnitRepository;
    }

    @Override
    public QuantityUnit createQuantityUnit() {
        return null;
    }

    @Override
    public QuantityUnit updateQuantityUnit() {
        return null;
    }

    @Override
    public QuantityUnit deleteQuantityUnit() {
        return null;
    }

    @Override
    public List<QuantityUnit> getListOfQuantityUnits() {
        List<QuantityUnit> quantityUnitList = this.quantityUnitRepository.findAll();
        if(quantityUnitList.isEmpty()){
            throw new ResourceNotFoundException("Quantity Units were not found");
        }
        return quantityUnitList;
    }

}
