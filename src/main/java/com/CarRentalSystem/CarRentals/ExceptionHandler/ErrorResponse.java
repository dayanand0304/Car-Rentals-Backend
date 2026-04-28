package com.CarRentalSystem.CarRentals.ExceptionHandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "Standard error response")
public class ErrorResponse {

    @Schema(example = "2026-04-28T10:15:30")
    private LocalDateTime timestamp;

    @Schema(example = "404")
    private int status;

    @Schema(example = "NOT_FOUND")
    private String error;

    @Schema(example = "Car not found with ID: 1")
    private String message;

    @Schema(example = "/cars/1")
    private String path;
}