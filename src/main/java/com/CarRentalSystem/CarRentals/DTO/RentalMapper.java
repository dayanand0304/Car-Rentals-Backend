package com.CarRentalSystem.CarRentals.DTO;

import com.CarRentalSystem.CarRentals.DTO.Response.RentalResponse;
import com.CarRentalSystem.CarRentals.Entities.Rental;

public class RentalMapper {
    public static RentalResponse response(Rental rental){
        return new RentalResponse(
                rental.getRentalId(),
                rental.getCar().getCarId(),
                rental.getCar().getCarModel(),
                rental.getCustomer().getCustomerId(),
                rental.getCustomer().getCustomerName(),
                rental.getRentalType(),
                rental.getDuration(),
                rental.getStartTime(),
                rental.getExpectedReturnTime(),
                rental.getStatus(),
                rental.isDamaged(),
                rental.getLateFee(),
                rental.getTotalPrice()
        );
    }
}
