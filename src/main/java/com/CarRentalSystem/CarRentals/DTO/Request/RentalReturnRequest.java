package com.CarRentalSystem.CarRentals.DTO.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RentalReturnRequest{

    @NotNull(message = "damaged field is Required")
    private Boolean damaged;

    @DecimalMin(value = "0.0",message = "damaged fee must be in positive")
    private BigDecimal damagedFee;
}

