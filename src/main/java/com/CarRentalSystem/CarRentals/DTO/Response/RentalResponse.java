package com.CarRentalSystem.CarRentals.DTO.Response;

import com.CarRentalSystem.CarRentals.Enums.BookingStatus;
import com.CarRentalSystem.CarRentals.Enums.RentalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RentalResponse {
    private Integer rentalId;

    private Integer carId;
    private String carBrand;
    private String carModel;

    private Integer customerId;
    private String customerName;

    private RentalType rentalType;
    private Integer duration;

    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;
    private LocalDateTime actualReturnTime;

    private BookingStatus status;


    private BigDecimal totalPrice;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;

    private boolean damaged;
    private BigDecimal damagedFee;

    private BigDecimal lateFee;
    private BigDecimal grandTotal;
}
