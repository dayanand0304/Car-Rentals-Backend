package com.CarRentalSystem.CarRentals.Security;

import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerInActiveException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerNotFoundException;
import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public UserDetails loadUserByUsername(String email) throws CustomerNotFoundException {

        Customer customer=customerRepository
                .findByCustomerEmailIgnoreCase(email)
                .orElseThrow(()->new CustomerNotFoundException());

        if(!customer.isActive()){
            throw new CustomerInActiveException();
        }

        return new org.springframework.security.core.userdetails.User(
                customer.getCustomerEmail(),
                customer.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_"+ customer.getRole().name()))
        );
    }
}
