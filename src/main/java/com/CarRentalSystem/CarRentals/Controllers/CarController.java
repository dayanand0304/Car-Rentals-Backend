package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.CarMapper;
import com.CarRentalSystem.CarRentals.DTO.Request.CarCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CarUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CarResponse;
import com.CarRentalSystem.CarRentals.Entities.Car;
import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import com.CarRentalSystem.CarRentals.Services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    /**
     * GET /cars
     * Supports filtering by brand, model, availability
     *
     * Examples:
     * /cars
     * /cars?brand=BMW
     * /cars?brand=BMW&model=X5
     * /cars?available=true
     * /cars?brand=BMW&available=true
     */
    @GetMapping
    public ResponseEntity<List<CarResponse>> getCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) SeatType seats,
            @RequestParam(required = false) Boolean available
    ) {

        List<CarResponse> cars;

        if (brand != null && model != null) {
            cars = carService.getCarsByBrandAndModel(brand,model);

        } else if (brand != null && Boolean.TRUE.equals(available)) {
            cars = carService.getCarsByBrandAndAvailableTrue(brand);

        } else if (brand != null) {
            cars = carService.getCarsByBrand(brand);

        }else if(fuelType!=null){
            cars = carService.getCarsByFuelType(fuelType);

        }else if(seats!=null){
            cars= carService.getCarsBySeatType(seats);

        } else if (Boolean.TRUE.equals(available)) {
            cars = carService.getAvailableCars();

        } else {
            cars = carService.getAllCars();
        }

        return ResponseEntity.ok(cars);
    }



    //GET CAR BY ID
    @GetMapping("/{carId}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Integer carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    //GET CAR BY ID
    @GetMapping("/active/{carId}")
    public ResponseEntity<CarResponse> getActiveCarById(@PathVariable Integer carId) {
        return ResponseEntity.ok(carService.getActiveCarById(carId));
    }

    //CHECK AVAILABILITY OF CAR BY CAR ID
    @GetMapping("/{carId}/available")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Integer carId){
        return ResponseEntity.ok(carService.isAvailable(carId));
    }

    //GET CARS BY PRICE RANGE
    @GetMapping("/price-range")
    public ResponseEntity<List<CarResponse>> getsCarsByPriceRange(@RequestParam Integer min,
                                                                  @RequestParam Integer max){
        return ResponseEntity.ok(carService.getCarsByPriceRange(min,max));
    }

    //GET CAR BY REGISTRATION NUMBER
    @GetMapping("/register-number")
    public ResponseEntity<CarResponse> getCarByRegisterNum(@RequestParam String number){
        return ResponseEntity.ok(carService.getCarByRegisterNum(number));
    }

    //GET CARS BY LAST FOUR DIGITS OF REGISTER NUMBER
    @GetMapping("/four-digits")
    public ResponseEntity<List<CarResponse>> getCarsByLastFourDigits(@RequestParam String number){
        return ResponseEntity.ok(carService.getCarsByLastRegisterNum(number));
    }

    //GET CARS BY ACTIVE
    @GetMapping("/active")
    public ResponseEntity<List<CarResponse>> getCarsByActive(@RequestParam Boolean active){
        return ResponseEntity.ok(carService.getCarsByActive(active));
    }

    // ADD CAR
    @PostMapping
    public ResponseEntity<CarResponse> addCar(
            @Valid @RequestBody CarCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.addCar(request));
    }

    // DELETE CAR
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    // UPDATE CAR
    @PutMapping("/{carId}")
    public ResponseEntity<CarResponse> updateCar(
            @PathVariable Integer carId,
            @RequestBody CarUpdateRequest request) {

        return ResponseEntity.ok(carService.updateCarDetails(carId,request));
    }

}
