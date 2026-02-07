package com.CarRentalSystem.CarRentals.Repositories;

import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);
    Optional<Customer> findByCustomerEmailIgnoreCase(String email);
    Optional<Customer> findByCustomerPhoneNo(String phoneNo);
    List<Customer> findByCustomerNameContainingIgnoreCaseOrCustomerEmailContainingIgnoreCaseOrCustomerPhoneNoContaining(String name,String email,String phoneNo);

    List<Customer> findByRole(Role role);
    List<Customer> findByActive(boolean active);
}
