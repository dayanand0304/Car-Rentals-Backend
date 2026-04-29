package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.Request.CarCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CarUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CarResponse;
import com.CarRentalSystem.CarRentals.DTO.Response.PageResponse;
import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import com.CarRentalSystem.CarRentals.ExceptionHandler.ErrorResponse;
import com.CarRentalSystem.CarRentals.Services.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Car APIs", description = "Operations related to car management")
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
public class CarController {

    private final CarService carService;

    //GET ALL CARS WITH FILTERS
    @Operation(
            summary = "Get Cars",
            description = "Fetch cars with optional filters like brand, model, fuel type, seat type, and availability"
    )
    @ApiResponse(responseCode = "200", description = "Cars fetched successfully",
            content = @Content(schema = @Schema(implementation = CarResponse.class)))
    @GetMapping
    public ResponseEntity<PageResponse<CarResponse>> getCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) SeatType seats,
            @RequestParam(required = false) Boolean available,
            @ParameterObject @PageableDefault(size = 20, sort = "carId") Pageable pageable
    ) {

        PageResponse<CarResponse> cars;

        if (brand != null && model != null) {
            cars = carService.getCarsByBrandAndModel(brand, model, pageable);

        } else if (brand != null && Boolean.TRUE.equals(available)) {
            cars = carService.getCarsByBrandAndAvailableTrue(brand, pageable);

        } else if (brand != null) {
            cars = carService.getCarsByBrand(brand, pageable);

        } else if (fuelType != null) {
            cars = carService.getCarsByFuelType(fuelType, pageable);

        } else if (seats != null) {
            cars = carService.getCarsBySeatType(seats, pageable);

        } else if (Boolean.TRUE.equals(available)) {
            cars = carService.getAvailableCars(pageable);

        } else {
            cars = carService.getAllCars(pageable);
        }

        return ResponseEntity.ok(cars);
    }

    // GET CAR BY ID
    @Operation(summary = "Get Car by ID",
            description = "Fetch a single car using its unique ID")
    @ApiResponse(responseCode = "200", description = "Car found",
            content = @Content(schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{carId}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Integer carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    // CHECK AVAILABILITY
    @Operation(summary = "Check Car Availability",
            description = "Check whether a car is available for booking")
    @ApiResponse(responseCode = "200", description = "Availability fetched")
    @GetMapping("/{carId}/available")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Integer carId) {
        return ResponseEntity.ok(carService.isAvailable(carId));
    }

    // GET CARS BY PRICE RANGE
    @Operation(summary = "Get Cars by Price Range",
            description = "Fetch cars within a given minimum and maximum price range")
    @ApiResponse(responseCode = "200", description = "Cars fetched successfully",
            content = @Content(schema = @Schema(implementation = CarResponse.class)))
    @GetMapping("/price-range")
    public ResponseEntity<PageResponse<CarResponse>> getsCarsByPriceRange(
            @RequestParam Integer min,
            @RequestParam Integer max,
            @ParameterObject @PageableDefault(size = 5, sort = "carRentPerDay") Pageable pageable) {

        return ResponseEntity.ok(carService.getCarsByPriceRange(min, max, pageable));
    }

    // GET CAR BY REGISTRATION NUMBER
    @Operation(summary = "Get Car by Registration Number",
            description = "Fetch a car using its registration number")
    @ApiResponse(responseCode = "200", description = "Car found",
            content = @Content(schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/register-number")
    public ResponseEntity<CarResponse> getCarByRegisterNum(@RequestParam String number) {
        return ResponseEntity.ok(carService.getCarByRegisterNum(number));
    }

    // GET CARS BY LAST 4 DIGITS
    @Operation(summary = "Get Cars by Last 4 Digits",
            description = "Fetch cars matching last four digits of registration number")
    @ApiResponse(responseCode = "200", description = "Cars fetched successfully",
            content = @Content(schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/last-four-digits")
    public ResponseEntity<PageResponse<CarResponse>> getCarsByLastFourDigits(
            @RequestParam String number,
            @ParameterObject @PageableDefault(size = 5, sort = "registrationNumber") Pageable pageable) {

        return ResponseEntity.ok(carService.getCarsByLastRegisterNum(number, pageable));
    }

    // GET CARS BY ACTIVE STATUS
    @Operation(summary = "Get Cars by Active Status",
            description = "Fetch cars based on whether they are active or inactive")
    @ApiResponse(responseCode = "200", description = "Cars fetched successfully",
            content = @Content(schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/active")
    public ResponseEntity<PageResponse<CarResponse>> getCarsByActive(
            @RequestParam Boolean active,
            @ParameterObject @PageableDefault(size = 5, sort = "carId") Pageable pageable) {

        return ResponseEntity.ok(carService.getCarsByActive(active, pageable));
    }

    // ADD CAR
    @Operation(summary = "Add New Car",
            description = "Create a new car (Admin only)")
    @ApiResponse(responseCode = "201", description = "Car created successfully",
            content = @Content(schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "400", description = "Validation error")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarResponse> addCar(
            @Valid @RequestBody CarCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.addCar(request));
    }

    // DEACTIVATE CAR
    @Operation(summary = "Deactivate Car",
            description = "Deactivate a car by ID (Admin only)")
    @ApiResponse(responseCode = "204", description = "Car deactivated successfully")
    @ApiResponse(responseCode = "404", description = "Car not found")
    @DeleteMapping("/{carId}/de-activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCar(@PathVariable Integer carId) {
        carService.deActivateCar(carId);
        return ResponseEntity.ok("Car With Id: "+carId+" Deactivated Successfully");
    }

    // REACTIVATE CAR
    @Operation(summary = "Reactivate Car",
            description = "Reactivate a car by ID (Admin only)")
    @ApiResponse(responseCode = "204", description = "Car reactivated successfully")
    @PutMapping("/{carId}/re-activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> reActivateCar(@PathVariable Integer carId) {
        carService.reActivateCar(carId);
        return ResponseEntity.ok("Car With Id: "+carId+" ReActivated Successfully");
    }

    // UPDATE CAR
    @Operation(summary = "Update Car Details",
            description = "Update car details partially (Admin only)")
    @ApiResponse(responseCode = "200", description = "Car updated successfully",
            content = @Content(schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PatchMapping("/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarResponse> updateCar(
            @PathVariable Integer carId,
            @Valid @RequestBody CarUpdateRequest request) {

        return ResponseEntity.ok(carService.updateCarDetails(carId, request));
    }
}