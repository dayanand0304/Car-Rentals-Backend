package com.CarRentalSystem.CarRentals.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CarUpdateRequest {
    private String carBrand;
    private String carModel;
    private Integer carRentPerDay;
}
