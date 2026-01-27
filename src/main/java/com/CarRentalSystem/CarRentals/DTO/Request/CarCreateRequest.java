package com.CarRentalSystem.CarRentals.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarCreateRequest {

    @NotBlank(message = "Car brand must not be blank")
    private String carBrand;

    @NotBlank(message = "Car model must not be blank")
    private String carModel;

    @NotNull
    @Positive(message = "Rent Must be Greater Than Zero")
    private Integer carRentPerDay;
}
