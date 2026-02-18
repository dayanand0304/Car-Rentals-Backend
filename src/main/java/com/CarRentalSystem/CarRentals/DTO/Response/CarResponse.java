package com.CarRentalSystem.CarRentals.DTO.Response;


import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarResponse {
    private Integer carId;
    private String carBrand;
    private String carModel;
    private String registrationNumber;
    private FuelType fuelType;
    private SeatType seats;
    private Integer carRentPerDay;
    private Boolean available;
    private Boolean active;
}
