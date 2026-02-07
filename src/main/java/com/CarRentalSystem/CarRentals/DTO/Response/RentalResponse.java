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
    private String carModel;
    private Integer customerId;
    private String customerName;
    private RentalType rentalType;
    private Integer duration;

    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;

    private BookingStatus status;
    private boolean damaged;
    private BigDecimal lateFee;
    private BigDecimal totalPrice;

}
