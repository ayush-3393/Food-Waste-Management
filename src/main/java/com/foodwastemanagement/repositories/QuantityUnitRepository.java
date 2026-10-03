package com.foodwastemanagement.repositories;

import com.foodwastemanagement.models.QuantityUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityUnitRepository extends JpaRepository<QuantityUnit, Long> {
    boolean existsByName(String name);
}
