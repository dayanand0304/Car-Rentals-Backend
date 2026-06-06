package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class InvalidRentalStateException extends RuntimeException {
    public InvalidRentalStateException(String message) {
        super(message);
    }
}
