package com.CarRentalSystem.CarRentals.CustomExceptions.Cars;

public class CarAlreadyExistsException extends RuntimeException{
    public CarAlreadyExistsException(String registrationNumber){
        super("Car with registration number: "+registrationNumber+" already Exits");
    }
}
