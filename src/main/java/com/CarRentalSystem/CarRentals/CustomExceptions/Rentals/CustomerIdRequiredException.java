package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class CustomerIdRequiredException extends RuntimeException {
    public CustomerIdRequiredException() {
        super("Customer id is required for admin bookings");
    }
}
