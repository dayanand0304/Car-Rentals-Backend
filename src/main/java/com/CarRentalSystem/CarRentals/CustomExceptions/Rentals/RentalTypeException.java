package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

import com.CarRentalSystem.CarRentals.Enums.RentalType;

public class RentalTypeException extends RuntimeException{
    public RentalTypeException(RentalType rentalType){
        super("Invalid rental type: " + rentalType + ". Allowed values: DAILY, HOURLY");
    }
}
