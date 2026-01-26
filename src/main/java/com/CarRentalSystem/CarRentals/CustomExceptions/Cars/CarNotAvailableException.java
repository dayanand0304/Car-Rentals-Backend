package com.CarRentalSystem.CarRentals.CustomExceptions.Cars;

public class CarNotAvailableException extends RuntimeException{
    public CarNotAvailableException(Integer carId){
        super("Car with id " + carId + " is currently rented and not available");
    }
}
