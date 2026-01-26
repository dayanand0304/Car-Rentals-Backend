package com.CarRentalSystem.CarRentals.DTO;

import com.CarRentalSystem.CarRentals.DTO.Request.CarCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CarResponse;
import com.CarRentalSystem.CarRentals.Entities.Car;

public class CarMapper {
    public static Car create(CarCreateRequest request){
        Car car=new Car();
        car.setCarBrand(request.getCarBrand());
        car.setCarModel(request.getCarModel());
        car.setCarRentPerDay(request.getCarRentPerDay());
        return car;
    }
    public static CarResponse response(Car car){
        return new CarResponse(
                car.getCarId(),
                car.getCarBrand(),
                car.getCarModel(),
                car.getCarRentPerDay(),
                car.getAvailable()
        );
    }
}
