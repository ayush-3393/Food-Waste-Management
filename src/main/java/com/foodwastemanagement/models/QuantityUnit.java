package com.foodwastemanagement.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quantity_units")
public class QuantityUnit extends BaseModel{
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "symbol", nullable = false, unique = true)
    private String symbol;

    @Column(name = "description")
    private String description;
}
