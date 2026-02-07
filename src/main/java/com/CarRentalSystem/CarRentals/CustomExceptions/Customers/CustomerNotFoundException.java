package com.CarRentalSystem.CarRentals.CustomExceptions.Customers;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Integer customerId){
        super("Customer with Id: "+customerId+" Not Found");
    }

    public CustomerNotFoundException(String customerName){
        super("Customer with : "+customerName+" Not Found");
    }

    public CustomerNotFoundException(){
        super("Customer Details with Above Keyword are Not Found");
    }
}
