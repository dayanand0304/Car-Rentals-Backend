package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.Request.CarCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CarUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CarResponse;
import com.CarRentalSystem.CarRentals.DTO.Response.PageResponse;
import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import com.CarRentalSystem.CarRentals.Services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
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
    public ResponseEntity<PageResponse<CarResponse>> getCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) SeatType seats,
            @RequestParam(required = false) Boolean available,
            @PageableDefault(size = 20, sort = "carId")Pageable pageable
            ) {

        PageResponse<CarResponse> cars;

        if (brand != null && model != null) {
            cars = carService.getCarsByBrandAndModel(brand,model,pageable);

        } else if (brand != null && Boolean.TRUE.equals(available)) {
            cars = carService.getCarsByBrandAndAvailableTrue(brand,pageable);

        } else if (brand != null) {
            cars = carService.getCarsByBrand(brand,pageable);

        }else if(fuelType!=null){
            cars = carService.getCarsByFuelType(fuelType,pageable);

        }else if(seats!=null){
            cars= carService.getCarsBySeatType(seats,pageable);

        } else if (Boolean.TRUE.equals(available)) {
            cars = carService.getAvailableCars(pageable);

        } else {
            cars = carService.getAllCars(pageable);
        }

        return ResponseEntity.ok(cars);
    }

    //GET CAR BY ID
    @GetMapping("/{carId}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Integer carId){
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    //CHECK AVAILABILITY OF CAR BY CAR ID
    @GetMapping("/{carId}/available")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Integer carId){
        return ResponseEntity.ok(carService.isAvailable(carId));
    }

    //GET CARS BY PRICE RANGE
    @GetMapping("/price-range")
    public ResponseEntity<PageResponse<CarResponse>> getsCarsByPriceRange(@RequestParam Integer min,
                                                                  @RequestParam Integer max,
                                                                  @PageableDefault(size = 5, sort = "carRentPerDay")Pageable pageable){
        return ResponseEntity.ok(carService.getCarsByPriceRange(min,max,pageable));
    }

    //GET CAR BY REGISTRATION NUMBER
    @GetMapping("/register-number")
    public ResponseEntity<CarResponse> getCarByRegisterNum(@RequestParam String number){
        return ResponseEntity.ok(carService.getCarByRegisterNum(number));
    }

    //GET CARS BY LAST FOUR DIGITS OF REGISTER NUMBER
    @GetMapping("/last-four-digits")
    public ResponseEntity<PageResponse<CarResponse>> getCarsByLastFourDigits(@RequestParam String number,
                                                                     @PageableDefault(size = 5, sort = "registrationNumber")Pageable pageable){
        return ResponseEntity.ok(carService.getCarsByLastRegisterNum(number,pageable));
    }

    //GET CARS BY ACTIVE
    @GetMapping("/active")
    public ResponseEntity<PageResponse<CarResponse>> getCarsByActive(@RequestParam Boolean active,
                                                             @PageableDefault(size = 5, sort = "carId")Pageable pageable){
        return ResponseEntity.ok(carService.getCarsByActive(active,pageable));
    }

    // ADD CAR
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarResponse> addCar(
            @Valid @RequestBody CarCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.addCar(request));
    }

    // DE-ACTIVATE CAR
    @DeleteMapping("/{carId}/de-activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer carId) {
        carService.deActivateCar(carId);
        return ResponseEntity.noContent().build();
    }

    //RE-ACTIVATE CAR
    @PutMapping("/{carId}/re-activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> reActivateCar(@PathVariable Integer carId){
        carService.reActivateCar(carId);
        return ResponseEntity.noContent().build();
    }

    // UPDATE CAR
    @PatchMapping("/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarResponse> updateCar(
            @PathVariable Integer carId,
            @RequestBody CarUpdateRequest request) {

        return ResponseEntity.ok(carService.updateCarDetails(carId,request));
    }
}
