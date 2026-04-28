package com.CarRentalSystem.CarRentals.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "DTO for returning a rented car")
public class RentalReturnRequest {

    @Schema(
            description = "Indicates whether the car is damaged",
            example = "true"
    )
    @NotNull(message = "damaged field is Required")
    private Boolean damaged;

    @Schema(
            description = "Damage fee (if applicable)",
            example = "500.00"
    )
    @DecimalMin(value = "0.0", message = "damaged fee must be in positive")
    private BigDecimal damagedFee;
}