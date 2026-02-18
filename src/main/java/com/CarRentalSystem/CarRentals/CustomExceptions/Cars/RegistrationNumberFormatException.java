package com.CarRentalSystem.CarRentals.CustomExceptions.Cars;

public class RegistrationNumberFormatException extends RuntimeException{
    public RegistrationNumberFormatException(String s){
        super(s);
    }
}
