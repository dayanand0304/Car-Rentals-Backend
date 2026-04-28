package com.CarRentalSystem.CarRentals.DTO.Response;

import com.CarRentalSystem.CarRentals.Enums.BookingStatus;
import com.CarRentalSystem.CarRentals.Enums.RentalType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO representing rental details")
public class RentalResponse {

    @Schema(example = "5001", description = "Rental ID")
    private Integer rentalId;

    @Schema(example = "1", description = "Car ID")
    private Integer carId;

    @Schema(example = "Toyota", description = "Car brand")
    private String carBrand;

    @Schema(example = "Innova", description = "Car model")
    private String carModel;

    @Schema(example = "101", description = "Customer ID")
    private Integer customerId;

    @Schema(example = "Ravi Kumar", description = "Customer name")
    private String customerName;

    @Schema(example = "DAILY", description = "Rental type")
    private RentalType rentalType;

    @Schema(example = "3", description = "Duration")
    private Integer duration;

    @Schema(example = "2026-04-28T10:00:00", description = "Start time")
    private LocalDateTime startTime;

    @Schema(example = "2026-05-01T10:00:00", description = "Expected return time")
    private LocalDateTime expectedReturnTime;

    @Schema(example = "2026-05-01T09:30:00", description = "Actual return time")
    private LocalDateTime actualReturnTime;

    @Schema(example = "COMPLETED", description = "Booking status")
    private BookingStatus status;

    @Schema(example = "3000.00", description = "Base price")
    private BigDecimal totalPrice;

    @Schema(example = "300.00", description = "Tax amount")
    private BigDecimal taxAmount;

    @Schema(example = "200.00", description = "Discount applied")
    private BigDecimal discountAmount;

    @Schema(example = "false", description = "Damage status")
    private boolean damaged;

    @Schema(example = "0.00", description = "Damage fee")
    private BigDecimal damagedFee;

    @Schema(example = "100.00", description = "Late return fee")
    private BigDecimal lateFee;

    @Schema(example = "3200.00", description = "Final payable amount")
    private BigDecimal grandTotal;
}