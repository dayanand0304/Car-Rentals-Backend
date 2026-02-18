package com.CarRentalSystem.CarRentals.DTO;

import com.CarRentalSystem.CarRentals.DTO.Request.CarCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CarUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CarResponse;
import com.CarRentalSystem.CarRentals.Entities.Car;

public class CarMapper {
    public static Car create(CarCreateRequest request){
        Car car=new Car();
        car.setCarBrand(request.getCarBrand());
        car.setCarModel(request.getCarModel());
        car.setRegistrationNumber(request.getRegistrationNumber());
        car.setFuelType(request.getFuelType());
        car.setSeats(request.getSeats());
        car.setCarRentPerDay(request.getCarRentPerDay());
        return car;
    }

    public static void update(Car car,CarUpdateRequest request) {
        car.setCarBrand(request.getCarBrand());
        car.setCarModel(request.getCarModel());
        car.setRegistrationNumber(request.getRegistrationNumber().toUpperCase());
        car.setFuelType(request.getFuelType());
        car.setSeats(request.getSeats());
        car.setCarRentPerDay(request.getCarRentPerDay());
    }

    public static CarResponse response(Car car){
        return new CarResponse(
                car.getCarId(),
                car.getCarBrand(),
                car.getCarModel(),
                car.getRegistrationNumber(),
                car.getFuelType(),
                car.getSeats(),
                car.getCarRentPerDay(),
                car.getAvailable(),
                car.getActive()
        );
    }
}
