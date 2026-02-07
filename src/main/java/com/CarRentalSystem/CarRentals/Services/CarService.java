package com.CarRentalSystem.CarRentals.Services;

import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarAlreadyExistsException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotActiveException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotAvailableException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotFoundException;
import com.CarRentalSystem.CarRentals.DTO.CarMapper;
import com.CarRentalSystem.CarRentals.DTO.Request.CarCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CarUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CarResponse;
import com.CarRentalSystem.CarRentals.Entities.Car;
import com.CarRentalSystem.CarRentals.Enums.FuelType;
import com.CarRentalSystem.CarRentals.Enums.SeatType;
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
    public List<CarResponse> getAllCars(){
        log.info("Fetching All Cars");
        return carRepository.findAll()
                .stream()
                .map(CarMapper::response)
                .toList();
    }

    //2.GET CAR DETAILS BY ID
    public CarResponse getCarById(Integer carId) {
        log.info("Fetching Car By carId:{}",carId);
        Car car=carRepository.findById(carId)
                .orElseThrow(()-> new CarNotFoundException(carId));

        return CarMapper.response(car);
    }

    public CarResponse getActiveCarById(Integer carId) {
        log.info("Fetching Active Car By carId:{}",carId);
        Car car=carRepository.findByCarIdAndActiveTrue(carId)
                .orElseThrow(()->new CarNotActiveException(carId));

        return CarMapper.response(car);
    }

    //3.GET AVAILABLE CARS
    public List<CarResponse> getAvailableCars(){
        log.info("Fetching Available Cars");
        return carRepository.findByAvailableTrue()
                .stream()
                .map(CarMapper::response)
                .toList();
    }

    //4.GET CAR DETAILS BY CAR BRAND
    public List<CarResponse> getCarsByBrand(String carBrand){
        log.info("Fetching All Cars By BrandName:{}",carBrand);
        return carRepository.findByCarBrandContainingIgnoreCase(carBrand)
                .stream()
                .map(CarMapper::response)
                .toList();
    }

    //5.GET AVAILABLE CAR BRAND DETAILS
    public List<CarResponse> getCarsByBrandAndAvailableTrue(String carBrand){
        log.info("Fetching All Available Cars By BrandName:{}",carBrand );
        return carRepository.findByCarBrandContainingIgnoreCaseAndAvailableTrue(carBrand)
                .stream()
                .map(CarMapper::response)
                .toList();
    }

    //6.GET CAR DETAILS BY CAR BRAND AND MODEL
    public List<CarResponse> getCarsByBrandAndModel(String carBrand, String carModel){
        log.info("Fetching All Cars By BrandName:{} and CarModel:{}",carBrand,carModel);

        return carRepository.findByCarBrandAndCarModelContainingIgnoreCase(carBrand,carModel)
                .stream()
                .map(CarMapper::response)
                .toList();
    }


    //7.CHECK IF CAR IS AVAILABLE OR NOT
    public boolean isAvailable(Integer carId){
        log.debug("checking Availability of CarId:{}",carId);
        Car car=carRepository.findById(carId)
                .orElseThrow(()->new CarNotFoundException(carId));
        return car.getAvailable();
    }

    //8.FIND CARS BY FUEL TYPE
    public List<CarResponse> getCarsByFuelType(FuelType fuelType){
        return carRepository.findByFuelType(fuelType)
                .stream()
                .map(CarMapper::response)
                .toList();
    }

    //9.FIND BY SEAT TYPE
    public List<CarResponse> getCarsBySeatType(SeatType seats){
        return carRepository.findBySeats(seats)
                .stream()
                .map(CarMapper::response)
                .toList();
    }

    //10.FIND CAR BY PRICE RANGE
    public List<CarResponse> getCarsByPriceRange(Integer min,Integer max){
        return carRepository.findByCarRentPerDayBetween(min, max)
                .stream()
                .map(CarMapper::response)
                .toList();
    }

    //11.FIND CAR BY REGISTRATION NUMBER
    public CarResponse getCarByRegisterNum(String number){
        Car car=carRepository.findByRegistrationNumber(number)
                .orElseThrow(()->new CarNotFoundException("Car With Registration Number: "+number+" not found"));

        return CarMapper.response(car);
    }

    //12.GET CARS BY LAST FOUR DIGITS OF REGISTER NUMBER
    public List<CarResponse> getCarsByLastRegisterNum(String number){
        return carRepository.findByRegistrationNumberEndingWith(number)
                .stream()
                .map(CarMapper::response)
                .toList();
    }

    //13.ADD CAR
    public CarResponse addCar(CarCreateRequest request){

        Car car=CarMapper.create(request);

        if(carRepository.existsByRegistrationNumber(car.getRegistrationNumber())){
            throw new CarAlreadyExistsException(car.getRegistrationNumber());
        }

        log.info("Adding New Car Of Brand:{} and Model:{}", car.getCarBrand(),car.getCarModel());

        Car saved=carRepository.save(car);
        log.info("Added New Car with id:{}",saved.getCarId());
        return CarMapper.response(saved);
    }

    //14.DELETE CAR
    @Transactional
    public void deleteCar(Integer carId){
        log.info("Attempting to delete car with id: {}", carId);

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));

        if (!car.getAvailable()) {
            throw new CarNotAvailableException(carId);
        }
        car.setActive(false);
        carRepository.save(car);
        log.info("Car with id:{} deleted successfully", carId);
    }

    //15.UPDATE CAR DETAILS
    @Transactional
    public CarResponse updateCarDetails(Integer carId, CarUpdateRequest updatedDetails){
        log.info("Updating car with id: {}", carId);
        return carRepository.findById(carId)
                .map(existingDetails->{
                    if(!existingDetails.getActive()){
                        throw new CarNotFoundException(carId);
                    }
                    if(updatedDetails.getCarBrand()!=null){
                        existingDetails.setCarBrand(updatedDetails.getCarBrand());
                    }
                    if(updatedDetails.getCarModel()!=null){
                        existingDetails.setCarModel(updatedDetails.getCarModel());
                    }
                    if(updatedDetails.getRegistrationNumber()!=null){
                        if(carRepository.existsByRegistrationNumber(updatedDetails.getRegistrationNumber())){
                            throw new CarAlreadyExistsException(updatedDetails.getRegistrationNumber());
                        }
                        existingDetails.setRegistrationNumber(updatedDetails.getRegistrationNumber());
                    }
                    if(updatedDetails.getFuelType()!=null){
                        existingDetails.setFuelType(updatedDetails.getFuelType());
                    }
                    if(updatedDetails.getSeats()!=null){
                        existingDetails.setSeats(updatedDetails.getSeats());
                    }
                    if(updatedDetails.getCarRentPerDay()!=null){
                        existingDetails.setCarRentPerDay(updatedDetails.getCarRentPerDay());
                    }
                    if (updatedDetails.getAvailable() != null) {
                        existingDetails.setAvailable(updatedDetails.getAvailable());
                    }

                    Car saved=carRepository.save(existingDetails);
                    log.info("Car with id:{} updated successfully", saved.getCarId());
                    return CarMapper.response(saved);
                })
                .orElseThrow(()->new CarNotFoundException(carId));
    }

    //16.FIND CAR BY ACTIVE
    public List<CarResponse> getCarsByActive(Boolean active){
        return carRepository.findByActive(active)
                .stream()
                .map(CarMapper::response)
                .toList();
    }
}
