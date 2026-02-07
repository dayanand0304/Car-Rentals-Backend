package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class RentalAlreadyCancelledException extends RuntimeException{
    public RentalAlreadyCancelledException(){
        super("Rental Already Cancelled");
    }
}
