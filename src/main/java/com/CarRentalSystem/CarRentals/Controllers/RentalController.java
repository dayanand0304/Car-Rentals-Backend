package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.Request.RentalCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.RentalReturnRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.PageResponse;
import com.CarRentalSystem.CarRentals.DTO.Response.RentalResponse;
import com.CarRentalSystem.CarRentals.Enums.BookingStatus;
import com.CarRentalSystem.CarRentals.Enums.RentalType;
import com.CarRentalSystem.CarRentals.Services.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RentalController {

    private final RentalService rentalService;

    //1.GET ALL RENTALS
    @GetMapping
    public ResponseEntity<PageResponse<RentalResponse>> getAllRentals(
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) Integer carId,
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false)RentalType rentalType,
            @PageableDefault(size = 5,sort="rentalId")Pageable pageable
            ){
        PageResponse<RentalResponse> rentals;

        if(customerId!=null && status!=null){
            rentals = rentalService.getRentalByCustomerIdAndStatus(customerId,status,pageable);
        }else if(customerId!=null){
            rentals = rentalService.getRentalsByCustomerId(customerId,pageable);
        }else if(carId!=null){
            rentals = rentalService.getRentalByCarId(carId,pageable);
        }else if(status!=null){
            rentals = rentalService.getRentalByStatus(status,pageable);
        }else if(rentalType!=null){
            rentals = rentalService.getRentalsByRentalType(rentalType,pageable);
        }else{
            rentals = rentalService.getAllRentals(pageable);
        }

        return ResponseEntity.ok(rentals);
    }

    //2.GET RENTAL BY RENTAL ID
    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Integer rentalId){
        return ResponseEntity.ok(rentalService.getRentalByRentalId(rentalId));
    }

    //GET ALL DAMAGED RENTALS
    @GetMapping("/damaged")
    public ResponseEntity<PageResponse<RentalResponse>> getAllDamagedRentals(@PageableDefault(size = 10,sort="rentalId")Pageable pageable){
        return ResponseEntity.ok(rentalService.getRentalsOfDamaged(pageable));
    }

    //10.GET OVER DUES RENTALS
    @GetMapping("/over-dues")
    public ResponseEntity<PageResponse<RentalResponse>> getOverDues(@PageableDefault(size = 10,sort="rentalId")Pageable pageable){
        return ResponseEntity.ok(rentalService.getAllOverdueRentals(pageable));
    }

    //7.RENT A CAR
    @PostMapping("/rent-car")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<RentalResponse> rentACar(@Valid @RequestBody RentalCreateRequest request){

        RentalResponse rental=rentalService.rentACar(
                request.getCarId(),
                request.getCustomerId(),
                request.getRentalType(),
                request.getDuration()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rental);
    }

    //8.RETURN A CAR
    @PutMapping("/{rentalId}/return")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<RentalResponse> returnCar(@PathVariable Integer rentalId,
                                                    @Valid @RequestBody RentalReturnRequest request){
        return ResponseEntity.ok(rentalService.returnACar(
                rentalId,
                request.getDamaged(),
                request.getDamagedFee()));
    }

    //9.CANCEL RENTAL
    @DeleteMapping("/{rentalId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<Void> cancelRental(@PathVariable Integer rentalId){
        rentalService.cancelCar(rentalId);
        return ResponseEntity.noContent().build();
    }

    //SET DAMAGED CAR TO AVAILABLE
    @PutMapping("/cars/{carId}/set-available")
    public ResponseEntity<Void> repairCar(@PathVariable Integer carId){
        rentalService.markCarAsRepaired(carId);
        return ResponseEntity.noContent().build();
    }
}
