package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.CarMapper;
import com.CarRentalSystem.CarRentals.DTO.Request.CarCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CarResponse;
import com.CarRentalSystem.CarRentals.Entities.Car;
import com.CarRentalSystem.CarRentals.Services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {


    private final CarService carService;

    //1.GET CAR DETAILS
    @GetMapping("/get-all")
    public ResponseEntity<List<CarResponse>> getAllCars(){
        List<CarResponse> cars=carService.getAllCars()
                .stream()
                .map(CarMapper::response)
                .toList();
        return ResponseEntity.ok(cars);
    }

    //2.GET CAR DETAILS BY ID
    @GetMapping("/get-car/{carId}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Integer carId){
        Car car=carService.getCarById(carId);
        return ResponseEntity.ok(CarMapper.response(car));
    }

    //3.GET AVAILABLE CARS
    @GetMapping("/get-available-cars")
    public ResponseEntity<List<CarResponse>> getAvailableCars(){
        List<CarResponse> cars=carService.getAvailableCars()
                .stream()
                .map(CarMapper::response)
                .toList();
        return ResponseEntity.ok(cars);
    }

    //4.GET CARS BY BRAND
    @GetMapping("/car-brand/{carBrand}")
    public ResponseEntity<List<CarResponse>> getCarsByBrand(@PathVariable String carBrand){
        List<CarResponse> cars=carService.carListByBrand(carBrand)
                .stream()
                .map(CarMapper::response)
                .toList();
        return ResponseEntity.ok(cars);
    }

    //5.GET AVAILABLE CARS BY CAR BRAND
    @GetMapping("/car-brand-available/{carBrand}")
    public ResponseEntity<List<CarResponse>> getAvailableCarsByBrand(@PathVariable String carBrand){
        List<CarResponse> cars=carService.getAvailableCarsByCarBrand(carBrand)
                .stream()
                .map(CarMapper::response)
                .toList();
        return ResponseEntity.ok(cars);
    }

    //6.GET CAR DETAILS BY CAR BRAND OR  MODEL
    @GetMapping("/search")
    public ResponseEntity<List<CarResponse>> getCarsByBrandAndModel(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model) {

        List<Car> cars;
        if (brand != null && model != null) {
            cars=carService.carListByBrandAndModel(brand,model);
        }else if(brand!=null){
            cars=carService.carListByBrand(brand);
        }else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cars.stream()
                .map(CarMapper::response).toList());
    }

    //7.CHECK IF CAR IS AVAILABLE OR NOT
    @GetMapping("/available/{carId}")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Integer carId){
        Boolean car=carService.isAvailable(carId);
        return ResponseEntity.ok(car);
    }

    //8.ADD CAR
    @PostMapping("/add-car")
    public ResponseEntity<CarResponse> addCar(@RequestBody CarCreateRequest car){
        Car newCar=CarMapper.create(car);
        Car saved=carService.addCar(newCar);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CarMapper.response(saved));
    }

    //9.DELETE CAR
    @DeleteMapping("/delete-car/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer carId){
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    //10.EDIT CAR DETAILS
    @PutMapping("/update-car/{carId}")
    public ResponseEntity<CarResponse> updateCarDetails(@PathVariable Integer carId,
                                                @RequestBody CarCreateRequest car){
        Car update=CarMapper.create(car);
        Car updated=carService.updateCarDetails(carId,update);
        return ResponseEntity.ok(CarMapper.response(updated));
    }
}
