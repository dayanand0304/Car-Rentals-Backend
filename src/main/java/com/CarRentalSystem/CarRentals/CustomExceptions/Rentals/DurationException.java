package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class DurationException extends RuntimeException{
    public DurationException(Integer duration){
        super("Invalid duration: " + duration + ". Duration must be greater than zero.");
    }
}
