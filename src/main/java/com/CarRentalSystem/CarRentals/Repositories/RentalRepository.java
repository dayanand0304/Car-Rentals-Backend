package com.CarRentalSystem.CarRentals.Repositories;

import com.CarRentalSystem.CarRentals.DTO.BookingStatus;
import com.CarRentalSystem.CarRentals.Entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Integer> {

    List<Rental> findByCustomerCustomerId(Integer customerId);

    List<Rental> findByStatus(BookingStatus status);

    List<Rental> findByCarCarId(Integer carId);

    List<Rental> findByCustomerCustomerIdAndStatus(
            Integer customerId,
            BookingStatus status);

    List<Rental> findByActualReturnTimeIsNullAndExpectedReturnTimeBefore(LocalDateTime now);
}
