package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarUpdateRequest {

    private String carBrand;

    private String carModel;

    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$",
            message = "Registration number not in correct format"
    )
    private String registrationNumber;

    private FuelType fuelType;

    private SeatType seats;

    @Positive(message = "Rent Must be Greater Than Zero")
    private Integer carRentPerDay;

    private Boolean available;
}
