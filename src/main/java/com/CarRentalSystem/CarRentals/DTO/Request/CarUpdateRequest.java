package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for updating car details (all fields are optional)")
public class CarUpdateRequest {

    @Schema(
            description = "Updated brand of the car",
            example = "Hyundai"
    )
    private String carBrand;

    @Schema(
            description = "Updated model of the car",
            example = "Venue"
    )
    private String carModel;

    @Schema(
            description = "Updated registration number of the car",
            example = "TS09XY5678"
    )
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$",
            message = "Registration number not in correct format"
    )
    private String registrationNumber;

    @Schema(
            description = "Updated fuel type",
            example = "DIESEL"
    )
    private FuelType fuelType;

    @Schema(
            description = "Updated seating type",
            example = "SEVEN_SEATER"
    )
    private SeatType seats;

    @Schema(
            description = "Updated rent per day",
            example = "1800"
    )
    @Positive(message = "Rent Must be Greater Than Zero")
    private Integer carRentPerDay;

    @Schema(
            description = "Availability status of the car",
            example = "true"
    )
    private Boolean available;
}
