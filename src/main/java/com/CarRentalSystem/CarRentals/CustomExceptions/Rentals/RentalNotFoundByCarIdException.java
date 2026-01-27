package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class RentalNotFoundByCarIdException extends RuntimeException{
    public RentalNotFoundByCarIdException(Integer carId){
        super("No Rentals for Car Id: "+carId);
    }
}
