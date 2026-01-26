package com.CarRentalSystem.CarRentals.Services;

import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotAvailableException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Cars.CarNotFoundException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerNotFoundException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Rentals.*;
import com.CarRentalSystem.CarRentals.Enums.BookingStatus;
import com.CarRentalSystem.CarRentals.Enums.RentalType;
import com.CarRentalSystem.CarRentals.Entities.Car;
import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Entities.Rental;
import com.CarRentalSystem.CarRentals.Repositories.CarRepository;
import com.CarRentalSystem.CarRentals.Repositories.CustomerRepository;
import com.CarRentalSystem.CarRentals.Repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RentalService {


    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;


    //1.GET ALL RENTALS
    public List<Rental> getAllRentals(){
        log.info("Fetching All Rentals");
        return rentalRepository.findAll();
    }

    //2.GET RENTAL BY ID
    public Rental getRentalByRentalId(Integer rentalId){
        log.info("Fetching All Rentals By Rental Id:{}",rentalId);
        return rentalRepository.findById(rentalId)
                .orElseThrow(()->new RentalNotFoundException(rentalId));
    }

    //3.GET RENTALS BY CUSTOMER ID
    public List<Rental> getRentalsByCustomerId(Integer customerId){
        log.info("Fetching All Rentals By Customer Id:{}",customerId);
        return rentalRepository.findByCustomerCustomerId(customerId);
    }

    //4.GET RENTALS BY CAR ID
    public List<Rental> getRentalByCarId(Integer carId){
        log.info("Fetching All Rentals By Car Id:{}",carId);
        return rentalRepository.findByCarCarId(carId);
    }

    //5.GET RENTALS BY STATUS
    public List<Rental> getRentalByStatus(BookingStatus status){
        log.info("Fetching All Rentals By status:{}",status);
        return rentalRepository.findByStatus(status);
    }

    //6.FIND BY CUSTOMER ID AND STATUS
    public List<Rental> getRentalByCustomerIdAndStatus(Integer customerId,BookingStatus status){
        log.info("Fetching All Rentals By Customer Id:{} With {}",customerId,status);
        return rentalRepository.findByCustomerCustomerIdAndStatus(customerId,status);
    }

    //7.RENT A CAR
    @Transactional
    public Rental rentACar(Integer carId,
                           Integer customerId,
                           RentalType rentalType,
                           Integer duration){

        log.info("Renting Process of {} Bases Started for customerId:{} and carId:{}",
                rentalType,customerId,carId);

        if(duration ==null || duration <=0){
            log.warn("Invalid Duration Provided:{}", duration);
            throw new DurationException(duration);
        }

        log.info("Fetching Customer With Id:{}",customerId);
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()-> {
                    log.error("Customer With Id {} Not Found",customerId);
                    return new CustomerNotFoundException(customerId);
                });

        log.info("Fetching Car With Id:{}",carId);
        Car car=carRepository.findById(carId)
                .orElseThrow(()->{
                    log.error("Car With Id:{} Not Found",carId);
                    return new CarNotFoundException(carId);
                });

        if(!car.getAvailable()){
            log.warn("Car With Id:{} is Not Available",carId);
            throw new CarNotAvailableException(carId);
        }

        if (rentalType == null) {
            throw new RentalTypeException(rentalType);
        }

        Rental rental=new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setRentalType(rentalType);
        car.setAvailable(false);
        rental.setStatus(BookingStatus.CONFIRMED);
        rental.setStartTime(LocalDateTime.now());

        if(rentalType==RentalType.DAILY){
            rental.setDuration(duration);
            rental.setExpectedReturnTime(rental.getStartTime().plusDays(duration));
        }else{
            rental.setDuration(duration);
            rental.setExpectedReturnTime(rental.getStartTime().plusHours(duration));
        }
        BigDecimal totalPrice=calculatePrice(car,rentalType,duration);

        rental.setTotalPrice(totalPrice);
        carRepository.save(car);

        Rental saved=rentalRepository.save(rental);
        log.info("Rental Created With RentalId:{}",saved.getRentalId());
        return saved;
    }

    //FOR CALCULATING PRICE FOR RENTAL
    private BigDecimal calculatePrice(
            Car car,
            RentalType rentalType,
            int duration
    ) {
        return switch (rentalType) {

            case DAILY ->
                    BigDecimal.valueOf(car.getCarRentPerDay())
                            .multiply(BigDecimal.valueOf(duration));

            case HOURLY -> {
                BigDecimal hourlyRate =
                        BigDecimal.valueOf(car.getCarRentPerDay())
                                .divide(BigDecimal.valueOf(24), 2, RoundingMode.HALF_UP);

                yield hourlyRate.multiply(BigDecimal.valueOf(duration));
            }
        };
    }

    //8.RETURN A CAR
    @Transactional
    public Rental returnACar(Integer rentalId){
        log.info("Fetching Rental With Rental Id:{}",rentalId);
        Rental rental=rentalRepository.findById(rentalId)
                .orElseThrow(()->{
                    log.error("Rental With Id:{} Not Found",rentalId);
                    return new RentalNotFoundException(rentalId);
                });

        if (rental.getActualReturnTime() != null) {
            throw new AlreadyReturnedException();
        }
        Car car=rental.getCar();

        LocalDateTime now=LocalDateTime.now();
        rental.setActualReturnTime(now);

        LocalDateTime expected=rental.getExpectedReturnTime();
        BigDecimal totalPrice=rental.getTotalPrice();

        if(now.isAfter(expected)){
            long extraHours= ChronoUnit.HOURS.between(expected,now);

            BigDecimal rentPerHour= BigDecimal.valueOf(car.getCarRentPerDay())
                    .divide(BigDecimal.valueOf(24),2,RoundingMode.HALF_UP);

            BigDecimal fineHour=BigDecimal.valueOf(50);

            BigDecimal extraCost= rentPerHour.add(fineHour)
                    .multiply(BigDecimal.valueOf(extraHours));

            totalPrice=totalPrice.add(extraCost);

            log.info("Late return for rentalId:{} extraHours:{} extraCost:{}", rentalId, extraHours, extraCost);
        }
        rental.setTotalPrice(totalPrice);
        rental.setStatus(BookingStatus.COMPLETED);
        car.setAvailable(true);
        carRepository.save(car);

        log.info("Car With Id:{} Marked as Available and Saved",car.getCarId());

        Rental saved=rentalRepository.save(rental);
        log.info("Rental with id:{} updated on return", saved.getRentalId());
        return saved;
    }

    //9.CANCEL A CAR
    @Transactional
    public String cancelCar(Integer rentalId){
        log.info("Fetching Rental With Rental Id:{}",rentalId);
        Rental rental=rentalRepository.findById(rentalId)
                .orElseThrow(()->{
                    log.error("Rental With Id:{} Not Found",rentalId);
                    return new RentalNotFoundException(rentalId);
                });

        if(rental.getActualReturnTime()!=null){
            log.warn("Request To Cancel After Return for RentalId:{}",rentalId);
            throw new CannotCancelException();
        }
        Car car=rental.getCar();
        car.setAvailable(true);
        rental.setStatus(BookingStatus.CANCELLED);
        carRepository.save(car);

        log.info("Car With Id:{} Marked as Available and Saved",car.getCarId());
        rentalRepository.save(rental);
        log.info("Car With Id:{} Cancelled",rentalId);
        return "Rental Cancelled";
    }

    //10.GET OVER DUES RENTALS
    public List<Rental> getAllOverdueRentals(){
        LocalDateTime now=LocalDateTime.now();
        return rentalRepository.findByActualReturnTimeIsNullAndExpectedReturnTimeBefore(now);
    }
}
