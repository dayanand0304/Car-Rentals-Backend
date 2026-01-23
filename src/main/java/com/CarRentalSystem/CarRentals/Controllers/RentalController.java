package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.BookingStatus;
import com.CarRentalSystem.CarRentals.DTO.RentalType;
import com.CarRentalSystem.CarRentals.Entities.Rental;
import com.CarRentalSystem.CarRentals.Services.RentalService;
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
    public ResponseEntity<List<Rental>> getAllRentals(){
        List<Rental> rentals=rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    //2.GET RENTAL BY RENTAL ID
    @GetMapping("/{rentalId}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer rentalId){
        Rental rental=rentalService.getRentalByRentalId(rentalId);
        return ResponseEntity.ok(rental);
    }

    //3.GET RENTALS BY CUSTOMER ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Rental>> getRentalByCustomer(@PathVariable Integer customerId){
        List<Rental> rentals=rentalService.getRentalsByCustomerId(customerId);
        return ResponseEntity.ok(rentals);
    }

    //4.GET RENTALS BY CAR ID
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Rental>> getRentalByCar(@PathVariable Integer carId){
        List<Rental> rentals=rentalService.getRentalByCarId(carId);
        return ResponseEntity.ok(rentals);
    }

    //5.GET RENTALS BY Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Rental>> getRentalByStatus(@PathVariable BookingStatus status){
        List<Rental> rentals=rentalService.getRentalByStatus(status);
        return ResponseEntity.ok(rentals);
    }

    //6.GET RENTALS BY CUSTOMER ID AND STATUS
    @GetMapping("/customer/{customerId}/{status}")
    public ResponseEntity<List<Rental>> getRentalByCustomerIdAndStatus(@PathVariable Integer customerId, @PathVariable BookingStatus status){
        List<Rental> rentals=rentalService.getRentalByCustomerIdAndStatus(customerId,status);
        return ResponseEntity.ok(rentals);
    }

    //7.RENT A CAR
    @PostMapping("/rent-car")
    public ResponseEntity<Rental> rentACar(@RequestParam Integer carId,
                                           @RequestParam Integer customerId,
                                           @RequestParam RentalType rentalType,
                                           @RequestParam Integer duration){

        Rental rental=rentalService.rentACar(carId,customerId,rentalType,duration);
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }

    //8.RETURN A CAR
    @PutMapping("/return-car/{rentalId}")
    public ResponseEntity<Rental> returnCar(@PathVariable Integer rentalId){
        Rental rental=rentalService.returnACar(rentalId);
        return ResponseEntity.ok(rental);
    }

    //9.CANCEL RENTAL
    @DeleteMapping("/cancel/{rentalId}")
    public ResponseEntity<String> cancelRental(@PathVariable Integer rentalId){
        String message=rentalService.cancelCar(rentalId);
        return ResponseEntity.ok(message);
    }

    //10.GET OVER DUES RENTALS
    @GetMapping("/over-dues")
    public ResponseEntity<List<Rental>> getOverDues(){
        List<Rental> rentals=rentalService.getAllOverdueRentals();
        return ResponseEntity.ok(rentals);
    }
}
