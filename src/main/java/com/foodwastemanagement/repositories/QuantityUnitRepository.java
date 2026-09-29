package com.foodwastemanagement.repositories;

import com.foodwastemanagement.models.QuantityUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityUnitRepository extends JpaRepository<QuantityUnit, Long> {
}
