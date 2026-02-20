package com.CarRentalSystem.CarRentals.Repositories;

import com.CarRentalSystem.CarRentals.Enums.BookingStatus;
import com.CarRentalSystem.CarRentals.Entities.Rental;
import com.CarRentalSystem.CarRentals.Enums.RentalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Integer> {

    @Override
    @EntityGraph(attributePaths = {"car","customer"})
    Page<Rental> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"car","customer"})
    Optional<Rental> findByRentalId(Integer rentalId);

    @EntityGraph(attributePaths = {"car","customer"})
    Page<Rental> findByCarCarId(Integer carId, Pageable pageable);

    @EntityGraph(attributePaths = {"car","customer"})
    Page<Rental> findByCustomerCustomerId(Integer customerId, Pageable pageable);

    @EntityGraph(attributePaths = {"car","customer"})
    Page<Rental> findByStatus(BookingStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"car","customer"})
    Page<Rental> findByRentalType(RentalType rentalType, Pageable pageable);

    @EntityGraph(attributePaths = {"car","customer"})
    Page<Rental> findByDamagedTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"car","customer"})
    Page<Rental> findByCustomerCustomerIdAndStatus(
            Integer customerId,
            BookingStatus status,
            Pageable pageable);

    @EntityGraph(attributePaths = {"car","customer"})
    Page<Rental> findByActualReturnTimeIsNullAndExpectedReturnTimeBefore(LocalDateTime now, Pageable pageable);
}
