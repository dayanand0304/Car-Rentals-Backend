package com.CarRentalSystem.CarRentals.DTO;

import com.CarRentalSystem.CarRentals.DTO.Response.RentalResponse;
import com.CarRentalSystem.CarRentals.Entities.Rental;

public class RentalMapper {
    public static RentalResponse response(Rental rental){
        return new RentalResponse(
                rental.getRentalId(),
                rental.getCar().getCarId(),
                rental.getCustomer().getCustomerId(),
                rental.getRentalType(),
                rental.getDuration(),
                rental.getTotalPrice(),
                rental.getStatus(),
                rental.getStartTime(),
                rental.getExpectedReturnTime(),
                rental.getActualReturnTime()
        );
    }
}
