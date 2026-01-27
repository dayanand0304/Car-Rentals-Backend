package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.CarMapper;
import com.CarRentalSystem.CarRentals.DTO.Request.CarCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CarUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CarResponse;
import com.CarRentalSystem.CarRentals.Entities.Car;
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
            @RequestParam(required = false) Boolean available
    ) {

        List<Car> cars;

        if (brand != null && model != null) {
            cars = carService.carListByBrandAndModel(brand, model);

        } else if (brand != null && Boolean.TRUE.equals(available)) {
            cars = carService.getAvailableCarsByCarBrand(brand);

        } else if (brand != null) {
            cars = carService.carListByBrand(brand);

        } else if (Boolean.TRUE.equals(available)) {
            cars = carService.getAvailableCars();

        } else {
            cars = carService.getAllCars();
        }

        return ResponseEntity.ok(
                cars.stream()
                        .map(CarMapper::response)
                        .toList()
        );
    }

    // GET CAR BY ID
    @GetMapping("/{carId}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Integer carId) {
        return ResponseEntity.ok(
                CarMapper.response(carService.getCarById(carId))
        );
    }

    // CHECK AVAILABILITY
    @GetMapping("/{carId}/availability")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Integer carId) {
        return ResponseEntity.ok(carService.isAvailable(carId));
    }

    // ADD CAR
    @PostMapping
    public ResponseEntity<CarResponse> addCar(
            @Valid @RequestBody CarCreateRequest request) {

        Car saved = carService.addCar(CarMapper.create(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CarMapper.response(saved));
    }

    // UPDATE CAR
    @PutMapping("/{carId}")
    public ResponseEntity<CarResponse> updateCar(
            @PathVariable Integer carId,
            @RequestBody CarUpdateRequest request) {

        Car updated = carService.updateCarDetails(
                carId, CarMapper.update(request));

        return ResponseEntity.ok(CarMapper.response(updated));
    }

    // DELETE CAR
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }
}
