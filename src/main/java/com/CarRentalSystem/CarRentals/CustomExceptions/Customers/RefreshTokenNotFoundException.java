package com.CarRentalSystem.CarRentals.CustomExceptions.Customers;

public class RefreshTokenNotFoundException extends RuntimeException{
    public RefreshTokenNotFoundException(){
        super("Refresh Token Not Found");
    }
}
