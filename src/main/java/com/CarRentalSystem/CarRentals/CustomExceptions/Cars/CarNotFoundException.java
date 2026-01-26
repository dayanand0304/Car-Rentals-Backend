package com.CarRentalSystem.CarRentals.CustomExceptions.Cars;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(Integer carId){
        super("Car With id: "+carId+" Not Found");
    }
}
