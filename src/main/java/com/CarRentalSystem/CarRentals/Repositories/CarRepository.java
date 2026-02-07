package com.CarRentalSystem.CarRentals.Repositories;

import com.CarRentalSystem.CarRentals.Entities.Car;
import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {

    List<Car> findByCarBrandContainingIgnoreCase(String carBrand);
    List<Car> findByCarBrandAndCarModelContainingIgnoreCase(String carBrand, String carModel);
    List<Car> findByFuelType(FuelType fuelType);
    List<Car> findBySeats(SeatType seats);
    List<Car> findByAvailableTrue();
    List<Car> findByCarBrandContainingIgnoreCaseAndAvailableTrue(String carBrand);
    List<Car> findByCarRentPerDayBetween(Integer min,Integer max);

    Optional<Car> findByRegistrationNumber(String number);
    List<Car> findByRegistrationNumberEndingWith(String number);
    boolean existsByRegistrationNumber(String number);

    Optional<Car> findByCarIdAndActiveTrue(Integer carId);
    List<Car> findAllByActiveTrue();
    List<Car> findByActive(Boolean active);
}
