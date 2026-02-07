package com.CarRentalSystem.CarRentals.CustomExceptions.Cars;

public class CarAlreadyExistsException extends RuntimeException{
    public CarAlreadyExistsException(String brand,String model){
        super("Car with brand: "+brand+" and model: "+model+" Already Exists");
    }

    public CarAlreadyExistsException(String registrationNumber){
        super("Car with registration number: "+registrationNumber+" not found");
    }
}
