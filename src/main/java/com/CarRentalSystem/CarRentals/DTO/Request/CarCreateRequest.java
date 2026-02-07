package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Registration Number must not blank")
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$",
            message = "Registration number not in correct format"
    )
    private String registrationNumber;

    @NotNull(message = "fuelType must not be null")
    private FuelType fuelType;

    @NotNull(message = "seats must not null")
    private SeatType seats;

    @NotNull(message = "Rent should not be null")
    @Positive(message = "Rent Must be Greater Than Zero")
    private Integer carRentPerDay;
}
