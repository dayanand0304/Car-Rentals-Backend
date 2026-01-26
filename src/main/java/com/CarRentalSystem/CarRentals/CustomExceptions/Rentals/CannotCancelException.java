package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class CannotCancelException extends RuntimeException{
    public CannotCancelException(){
        super("Car is Already Returned..Cannot Cancel");
    }
}
