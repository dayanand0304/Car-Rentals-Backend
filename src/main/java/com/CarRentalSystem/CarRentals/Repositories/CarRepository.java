package com.CarRentalSystem.CarRentals.Repositories;

import com.CarRentalSystem.CarRentals.Entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {
    List<Car> findByCarBrand(String carBrand);

    List<Car> findByCarBrandAndCarModel(String carBrand, String carModel);

    List<Car> findByAvailableTrue();

    List<Car> findByCarBrandAndAvailableTrue(String carBrand);

    boolean existsByCarIdAndAvailableTrue(Integer carId);

    @Modifying
    @Query("UPDATE Car c SET c.available = false WHERE c.carId = :carId AND c.available = true")
    int markCarAsBooked(Integer carId);

    @Modifying
    @Query("UPDATE Car c SET c.available = true WHERE c.carId = :carId AND c.available = false")
    int markCarAsReturned(Integer carId);
}
