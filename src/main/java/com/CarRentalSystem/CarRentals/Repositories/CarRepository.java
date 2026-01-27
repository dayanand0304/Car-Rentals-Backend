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
}
