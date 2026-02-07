package com.CarRentalSystem.CarRentals.CustomExceptions.Cars;

public class CarNotActiveException extends RuntimeException{
    public CarNotActiveException(Integer carId){
        super("Car with Id: "+carId+" not in active");
    }
}
