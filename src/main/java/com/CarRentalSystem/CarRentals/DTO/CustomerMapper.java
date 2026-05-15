package com.CarRentalSystem.CarRentals.DTO;

import com.CarRentalSystem.CarRentals.DTO.Request.RegisterRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.Entities.Customer;

public class CustomerMapper {
    public static Customer create(RegisterRequest request){
        Customer customer=new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setPassword(request.getPassword());
        customer.setRole(request.getRole());
        return customer;
    }

    public static CustomerResponse response(Customer customer){
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerEmail(),
                customer.getRole(),
                customer.isActive()
        );
    }
}
