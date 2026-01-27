package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.RentalMapper;
import com.CarRentalSystem.CarRentals.DTO.Request.RentalCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.RentalResponse;
import com.CarRentalSystem.CarRentals.Enums.BookingStatus;
import com.CarRentalSystem.CarRentals.Entities.Rental;
import com.CarRentalSystem.CarRentals.Services.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    //1.GET ALL RENTALS
    @GetMapping("/get-all")
    public ResponseEntity<List<RentalResponse>> getAllRentals(){
        List<RentalResponse> rentals=rentalService.getAllRentals()
                .stream()
                .map(RentalMapper::response)
                .toList();
        return ResponseEntity.ok(rentals);
    }

    //2.GET RENTAL BY RENTAL ID
    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Integer rentalId){
        Rental rental=rentalService.getRentalByRentalId(rentalId);
        return ResponseEntity.ok(RentalMapper.response(rental));
    }

    //3.GET RENTALS BY CUSTOMER ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RentalResponse>> getRentalByCustomer(@PathVariable Integer customerId){
        List<RentalResponse> rentals=rentalService.getRentalsByCustomerId(customerId)
                .stream()
                .map(RentalMapper::response)
                .toList();
        return ResponseEntity.ok(rentals);
    }

    //4.GET RENTALS BY CAR ID
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<RentalResponse>> getRentalByCar(@PathVariable Integer carId){
        List<RentalResponse> rentals=rentalService.getRentalByCarId(carId)
                .stream()
                .map(RentalMapper::response)
                .toList();
        return ResponseEntity.ok(rentals);
    }

    //5.GET RENTALS BY Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RentalResponse>> getRentalByStatus(@PathVariable BookingStatus status){
        List<RentalResponse> rentals=rentalService.getRentalByStatus(status)
                .stream()
                .map(RentalMapper::response)
                .toList();
        return ResponseEntity.ok(rentals);
    }

    //6.GET RENTALS BY CUSTOMER ID AND STATUS
    @GetMapping("/customer/{customerId}/{status}")
    public ResponseEntity<List<RentalResponse>> getRentalByCustomerIdAndStatus(@PathVariable Integer customerId, @PathVariable BookingStatus status){
        List<RentalResponse> rentals=rentalService.getRentalByCustomerIdAndStatus(customerId,status)
                .stream()
                .map(RentalMapper::response)
                .toList();
        return ResponseEntity.ok(rentals);
    }

    //7.RENT A CAR
    @PostMapping("/rent-car")
    public ResponseEntity<RentalResponse> rentACar(@Valid @RequestBody RentalCreateRequest request){

        Rental rental=rentalService.rentACar(
                request.getCarId(),
                request.getCustomerId(),
                request.getRentalType(),
                request.getDuration()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RentalMapper.response(rental));
    }

    //8.RETURN A CAR
    @PutMapping("/return-car/{rentalId}")
    public ResponseEntity<RentalResponse> returnCar(@PathVariable Integer rentalId){
        Rental rental=rentalService.returnACar(rentalId);
        return ResponseEntity.ok(RentalMapper.response(rental));
    }

    //9.CANCEL RENTAL
    @DeleteMapping("/cancel/{rentalId}")
    public ResponseEntity<String> cancelRental(@PathVariable Integer rentalId){
        String message=rentalService.cancelCar(rentalId);
        return ResponseEntity.ok(message);
    }

    //10.GET OVER DUES RENTALS
    @GetMapping("/over-dues")
    public ResponseEntity<List<RentalResponse>> getOverDues(){
        List<RentalResponse> rentals=rentalService.getAllOverdueRentals()
                .stream()
                .map(RentalMapper::response)
                .toList();
        return ResponseEntity.ok(rentals);
    }
}
