package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class RentalNotFoundByCustomerIdException extends RuntimeException{
    public RentalNotFoundByCustomerIdException(Integer customerId){
        super("Rental Details of Customer Id: "+customerId+" Not Found");
    }
}
