package com.foodwastemanagement.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Setter
@Getter
public class Coordinates {
    @DecimalMin(value = "-90.0", message = "latitude must be at least -90")
    @DecimalMax(value = "90.0", message = "latitude must not exceed 90")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "longitude must be at least -180")
    @DecimalMax(value = "180.0", message = "longitude must not exceed 180")
    private Double longitude;
}
