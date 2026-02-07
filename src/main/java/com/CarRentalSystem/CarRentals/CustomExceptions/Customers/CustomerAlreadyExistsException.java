package com.CarRentalSystem.CarRentals.CustomExceptions.Customers;

public class CustomerAlreadyExistsException extends RuntimeException{
    public CustomerAlreadyExistsException(){
        super("Customer Already exist with these email or phone number");
    }
}
