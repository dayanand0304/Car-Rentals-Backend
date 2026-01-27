package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.RentalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalCreateRequest {

    @NotNull(message = "Car id must not be null")
    @Positive(message = "Car id should be positive")
    private Integer carId;

    @NotNull(message = "Customer id must not be null")
    @Positive(message = "Customer id should be positive")
    private Integer customerId;

    @NotNull(message = "Rental type must not be null")
    private RentalType rentalType;

    @NotNull(message = "Duration must not be null")
    @Positive(message = "Duration must be greater than zero")
    private Integer duration;
}
