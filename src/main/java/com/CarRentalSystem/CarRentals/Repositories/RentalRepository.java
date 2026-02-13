package com.CarRentalSystem.CarRentals.Repositories;

import com.CarRentalSystem.CarRentals.Enums.BookingStatus;
import com.CarRentalSystem.CarRentals.Entities.Rental;
import com.CarRentalSystem.CarRentals.Enums.RentalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Integer> {

    Page<Rental> findByCarCarId(Integer carId, Pageable pageable);
    Page<Rental> findByCustomerCustomerId(Integer customerId, Pageable pageable);

    Page<Rental> findByStatus(BookingStatus status, Pageable pageable);

    Page<Rental> findByRentalType(RentalType rentalType, Pageable pageable);

    Page<Rental> findByDamagedTrue(Pageable pageable);

    Page<Rental> findByCarCarIdAndStatus(
            Integer carId,
            BookingStatus status,
            Pageable pageable);

    Page<Rental> findByCustomerCustomerIdAndStatus(
            Integer customerId,
            BookingStatus status,
            Pageable pageable);

    Page<Rental> findByActualReturnTimeIsNullAndExpectedReturnTimeBefore(LocalDateTime now, Pageable pageable);
}
