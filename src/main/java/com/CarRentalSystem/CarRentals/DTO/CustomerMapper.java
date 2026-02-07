package com.CarRentalSystem.CarRentals.DTO;

import com.CarRentalSystem.CarRentals.DTO.Request.CustomerCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.Entities.Customer;

public class CustomerMapper {
    public static Customer create(CustomerCreateRequest request){
        Customer customer=new Customer();
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerPhoneNo(request.getCustomerPhoneNo());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setPassword(request.getPassword());
        customer.setRole(request.getRole());
        return customer;
    }
    public static void update(Customer customer,CustomerUpdateRequest request){
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerPhoneNo(request.getCustomerPhoneNo());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setPassword(request.getPassword());
    }

    public static CustomerResponse response(Customer customer){
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerPhoneNo(),
                customer.getCustomerEmail(),
                customer.getRole(),
                customer.getRentals()
        );
    }
}
