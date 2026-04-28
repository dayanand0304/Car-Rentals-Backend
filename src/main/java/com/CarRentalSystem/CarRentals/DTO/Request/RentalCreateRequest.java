package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.RentalType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for creating a rental booking")
public class RentalCreateRequest {

    @Schema(
            description = "ID of the car to be rented",
            example = "1"
    )
    @NotNull(message = "Car id must not be null")
    @Positive(message = "Car id should be positive")
    private Integer carId;

    @Schema(
            description = "ID of the customer renting the car",
            example = "101"
    )
    @NotNull(message = "Customer id must not be null")
    @Positive(message = "Customer id should be positive")
    private Integer customerId;

    @Schema(
            description = "Type of rental",
            example = "DAILY"
    )
    @NotNull(message = "Rental type must not be null")
    private RentalType rentalType;

    @Schema(
            description = "Duration of rental (in days or hours based on rental type)",
            example = "3"
    )
    @NotNull(message = "Duration must not be null")
    @Positive(message = "Duration must be greater than zero")
    private Integer duration;
}