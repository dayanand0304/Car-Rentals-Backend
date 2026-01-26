package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.RentalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalCreateRequest {
    private Integer carId;
    private Integer customerId;
    private RentalType rentalType;
    private Integer duration;
}
