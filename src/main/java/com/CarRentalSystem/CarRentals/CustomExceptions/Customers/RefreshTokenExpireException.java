package com.CarRentalSystem.CarRentals.CustomExceptions.Customers;

public class RefreshTokenExpireException extends RuntimeException{
    public RefreshTokenExpireException(){
        super("Token Expired");
    }
}
