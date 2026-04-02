package com.CarRentalSystem.CarRentals.CustomExceptions.Customers;

public class CustomerInActiveException extends RuntimeException{
    public CustomerInActiveException(){
        super("Customer is Inactive");
    }
}
