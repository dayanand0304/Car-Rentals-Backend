package com.CarRentalSystem.CarRentals.CustomExceptions.Rentals;

public class DamagedFeeNullException extends RuntimeException{
    public DamagedFeeNullException(){
        super("Damaged fee Required");
    }
}
