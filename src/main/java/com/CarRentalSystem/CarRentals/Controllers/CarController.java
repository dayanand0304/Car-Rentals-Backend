package com.CarRentalSystem.CarRentals.Controllers;

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
    public ResponseEntity<List<Car>> getAllCars(){
        List<Car> cars=carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    //2.GET CAR DETAILS BY ID
    @GetMapping("/get-car/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer carId){
        Car car=carService.getCarById(carId);
        return ResponseEntity.ok(car);
    }

    //3.GET AVAILABLE CARS
    @GetMapping("/get-available-cars")
    public ResponseEntity<List<Car>> getAvailableCars(){
        List<Car> cars=carService.getAvailableCars();
        return ResponseEntity.ok(cars);
    }

    //4.GET CARS BY BRAND
    @GetMapping("/car-brand/{carBrand}")
    public ResponseEntity<List<Car>> getCarsByBrand(@PathVariable String carBrand){
        List<Car> cars=carService.carListByBrand(carBrand);
        return ResponseEntity.ok(cars);
    }

    //5.GET AVAILABLE CARS BY CAR BRAND
    @GetMapping("/car-brand-available/{carBrand}")
    public ResponseEntity<List<Car>> getAvailableCarsByBrand(@PathVariable String carBrand){
        List<Car> cars=carService.getAvailableCarsByCarBrand(carBrand);
        return ResponseEntity.ok(cars);
    }

    //6.GET CAR DETAILS BY CAR BRAND OR  MODEL
    @GetMapping("/search")
    public ResponseEntity<List<Car>> getCarsByBrandAndModel(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model) {

        if (brand != null && model != null) {
            return ResponseEntity.ok(carService.carListByBrandAndModel(brand,model));
        }else if(brand!=null){
            return ResponseEntity.ok(carService.carListByBrand(brand));
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    //7.CHECK IF CAR IS AVAILABLE OR NOT
    @GetMapping("/available/{carId}")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Integer carId){
        Boolean car=carService.isAvailable(carId);
        return ResponseEntity.ok(car);
    }

    //8.ADD CAR
    @PostMapping("/add-car")
    public ResponseEntity<Car> addCar(@RequestBody Car car){
        Car newCar=carService.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCar);
    }

    //9.DELETE CAR
    @DeleteMapping("/delete-car/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Integer carId){
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    //10.EDIT CAR DETAILS
    @PutMapping("/update-car/{carId}")
    public ResponseEntity<Car> updateCarDetails(@PathVariable Integer carId,
                                                @RequestBody Car update){
        Car car=carService.updateCarDetails(carId,update);
        if(car!=null){
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
