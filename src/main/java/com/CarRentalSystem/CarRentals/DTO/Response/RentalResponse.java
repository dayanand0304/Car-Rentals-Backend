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
    private Integer customerId;

    private RentalType rentalType;
    private Integer duration;

    private BigDecimal totalPrice;
    private BookingStatus status;

    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;
    private LocalDateTime actualReturnTime;

}
