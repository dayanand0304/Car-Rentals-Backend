package com.CarRentalSystem.CarRentals.DTO.Response;

import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO representing car details in response")
public class CarResponse {

    @Schema(example = "1", description = "Unique ID of the car")
    private Integer carId;

    @Schema(example = "Toyota", description = "Car brand")
    private String carBrand;

    @Schema(example = "Innova Crysta", description = "Car model")
    private String carModel;

    @Schema(example = "AP01AB1234", description = "Registration number")
    private String registrationNumber;

    @Schema(example = "PETROL", description = "Fuel type")
    private FuelType fuelType;

    @Schema(example = "SEVEN_SEATER", description = "Seating capacity")
    private SeatType seats;

    @Schema(example = "1500", description = "Rent per day")
    private Integer carRentPerDay;

    @Schema(example = "true", description = "Availability status")
    private Boolean available;

    @Schema(example = "true", description = "Soft delete status")
    private Boolean active;
}