package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for creating a new car in the system")
public class CarCreateRequest {

    @Schema(
            description = "Brand of the car",
            example = "Toyota"
    )
    @NotBlank(message = "Car brand must not be blank")
    private String carBrand;

    @Schema(
            description = "Model of the car",
            example = "Innova Crysta"
    )
    @NotBlank(message = "Car model must not be blank")
    private String carModel;

    @Schema(
            description = "Unique registration number of the car",
            example = "AP01AB1234"
    )
    @NotBlank(message = "Registration Number must not blank")
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$",
            message = "Registration number not in correct format"
    )
    private String registrationNumber;

    @Schema(
            description = "Fuel type of the car",
            example = "PETROL"
    )
    @NotNull(message = "fuelType must not be null")
    private FuelType fuelType;

    @Schema(
            description = "Seating capacity type",
            example = "FIVE_SEATER"
    )
    @NotNull(message = "seats must not null")
    private SeatType seats;

    @Schema(
            description = "Rental price per day",
            example = "1500"
    )
    @NotNull(message = "Rent should not be null")
    @Positive(message = "Rent Must be Greater Than Zero")
    private Integer carRentPerDay;
}
