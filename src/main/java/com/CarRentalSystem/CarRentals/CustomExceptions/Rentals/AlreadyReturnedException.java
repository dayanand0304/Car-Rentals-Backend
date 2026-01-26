package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class AlreadyReturnedException extends RuntimeException{
    public AlreadyReturnedException(){
        super("Car is Already Returned");
    }
}
