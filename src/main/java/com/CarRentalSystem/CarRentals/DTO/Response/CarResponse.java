package com.CarRentalSystem.CarRentals.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarResponse {

    private Integer carId;
    private String carBrand;
    private String carModel;
    private Integer carRentPerDay;
    private Boolean available;
}
