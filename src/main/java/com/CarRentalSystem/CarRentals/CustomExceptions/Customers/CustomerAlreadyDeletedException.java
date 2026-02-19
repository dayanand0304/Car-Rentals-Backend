package com.CarRentalSystem.CarRentals.CustomExceptions.Customers;

public class CustomerAlreadyDeletedException extends RuntimeException{
    public CustomerAlreadyDeletedException(Integer customerId){
        super("customer with id: "+customerId+" already Deleted");
    }
}
