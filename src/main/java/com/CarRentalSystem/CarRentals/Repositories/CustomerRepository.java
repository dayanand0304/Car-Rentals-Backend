package com.CarRentalSystem.CarRentals.Repositories;

import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Page<Customer> findByCustomerNameContainingIgnoreCase(String customerName, Pageable pageable);

    boolean existsByCustomerEmail(String email);
    Optional<Customer> findByCustomerEmailIgnoreCase(String email);


    Page<Customer> findByRole(Role role, Pageable pageable);
    Page<Customer> findByActive(boolean active, Pageable pageable);
}
