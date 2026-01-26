package com.CarRentalSystem.CarRentals.Services;

import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotAvailableException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotFoundException;
import com.CarRentalSystem.CarRentals.Entities.Car;
import com.CarRentalSystem.CarRentals.Repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {


    private final CarRepository carRepository;

    //1.GET ALL CAR DETAILS
    public List<Car> getAllCars(){
        log.info("Fetching All Cars");
        return carRepository.findAll();
    }

    //2.GET CAR DETAILS BY ID
    public Car getCarById(Integer carId) {
        log.info("Fetching Car By carId:{}",carId);
        return carRepository.findById(carId)
                .orElseThrow(()-> new CarNotFoundException(carId));
    }

    //3.GET AVAILABLE CARS
    public List<Car> getAvailableCars(){
        log.info("Fetching Available Cars");
        return carRepository.findByAvailableTrue();
    }

    //4.GET CAR DETAILS BY CAR BRAND
    public List<Car> carListByBrand(String carBrand){
        log.info("Fetching All Cars By BrandName:{}",carBrand);
        return carRepository.findByCarBrand(carBrand);
    }

    //5.GET AVAILABLE CAR BRAND DETAILS
    public List<Car> getAvailableCarsByCarBrand(String carBrand){
        log.info("Fetching All Available Cars By BrandName:{}",carBrand );
        return carRepository.findByCarBrandAndAvailableTrue(carBrand);
    }

    //6.GET CAR DETAILS BY CAR BRAND AND MODEL
    public List<Car> carListByBrandAndModel(String carBrand, String carModel){
        log.info("Fetching All Cars By BrandName:{} and CarModel:{}",carBrand,carModel);
        return carRepository.findByCarBrandAndCarModel(carBrand,carModel);
    }

    //7.CHECK IF CAR IS AVAILABLE OR NOT
    public boolean isAvailable(Integer carId){
        log.debug("checking Availability of CarId:{}",carId);
        return carRepository.existsByCarIdAndAvailableTrue(carId);
    }

    //8.ADD CAR
    public Car addCar(Car car){
        log.info("Adding New Car Of Brand:{} and Model:{}", car.getCarBrand(),car.getCarModel());
        Car saved=carRepository.save(car);
        log.info("Added New Car with id:{}",saved.getCarId());
        return saved;
    }

    //9.DELETE CAR
    public void deleteCar(Integer carId){
        log.info("Attempting to delete car with id: {}", carId);

        if (!carRepository.existsById(carId)) {
            log.warn("Car with id:{} not found", carId);
            throw new CarNotFoundException(carId);
        }

        if(!carRepository.existsByCarIdAndAvailableTrue(carId)){
            log.error("Car With Id:{} is Rented, cannot delete",carId);
            throw new CarNotAvailableException(carId);
        }
        carRepository.deleteById(carId);
        log.info("Car With Id:{} is deleted",carId);
    }

    //10.EDIT CAR DETAILS
    @Transactional
    public Car updateCarDetails(Integer carId,Car updatedDetails){
        log.info("Updating car with id: {}", carId);
        return carRepository.findById(carId)
                .map(existingDetails->{
                    if(updatedDetails.getCarBrand()!=null){
                        existingDetails.setCarBrand(updatedDetails.getCarBrand());
                    }
                    if(updatedDetails.getCarModel()!=null){
                        existingDetails.setCarModel(updatedDetails.getCarModel());
                    }
                    if(updatedDetails.getCarRentPerDay()!=null){
                        existingDetails.setCarRentPerDay(updatedDetails.getCarRentPerDay());
                    }
                    existingDetails.setAvailable(updatedDetails.getAvailable());

                    Car saved=carRepository.save(existingDetails);
                    log.info("Car with id:{} updated successfully", saved.getCarId());
                    return saved;

                })
                .orElseThrow(()->new CarNotFoundException(carId));
    }
}
