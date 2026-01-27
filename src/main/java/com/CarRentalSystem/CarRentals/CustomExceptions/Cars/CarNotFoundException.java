package com.CarRentalSystem.CarRentals.CustomExceptions.Cars;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(Integer carId){
        super("Car With id: "+carId+" Not Found");
    }

    public CarNotFoundException(String brand,String model){
        super("Car with Brand Name: "+brand+" and With model: "+model+" Not found");
    }

    public CarNotFoundException(String brand){
        super("Car with Brand Name: "+brand+" Not found");
    }

    public CarNotFoundException(){
        super("No Cars Are in the Inventory");
    }
}
