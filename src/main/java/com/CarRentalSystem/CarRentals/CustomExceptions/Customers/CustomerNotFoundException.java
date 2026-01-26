package com.CarRentalSystem.CarRentals.CustomExceptions.Customers;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Integer customerId){
        super("Customer with Id: "+customerId+" Not Found");
    }
}
