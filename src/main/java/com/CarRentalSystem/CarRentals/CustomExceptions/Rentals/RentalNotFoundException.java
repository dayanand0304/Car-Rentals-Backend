package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class RentalNotFoundException extends RuntimeException{
    public RentalNotFoundException(Integer rentalId){
        super("Rental With Id: "+rentalId+" Not Found");
    }
}
