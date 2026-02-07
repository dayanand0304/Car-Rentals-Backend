package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class RentalAlreadyReturnedException extends RuntimeException{
    public RentalAlreadyReturnedException(){
        super("Car is Already Returned");
    }
}
