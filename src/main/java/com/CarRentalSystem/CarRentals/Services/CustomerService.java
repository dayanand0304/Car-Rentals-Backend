package com.CarRentalSystem.CarRentals.Services;

import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerNotFoundException;
import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    //1.GET ALL CUSTOMERS
    public List<Customer> getAllCustomers(){
        log.info("Fetching All Customers");
        return customerRepository.findAll();
    }

    //2.GET CUSTOMER BY CUSTOMER ID
    public Customer getCustomerById(Integer customerId){
        log.info("Fetching All Customers By Id:{}",customerId);
        return customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException(customerId));
    }

    //3.GET CUSTOMER BY CUSTOMER NAME BY CASE-INSENSITIVE
    public List<Customer> getCustomerByName(String customerName){
        log.info("Fetching All Customers By Customer Name:{}",customerName);
        return customerRepository.findByCustomerNameIgnoreCase(customerName);
    }

    //4.GET CUSTOMER BY PARTIAL CONTAINING AND CASE-INSENSITIVE SEARCH
    public List<Customer> searchCustomers(String keyword){
        log.info("Fetching All Customers By keyword ");
        return customerRepository
                .findByCustomerNameContainingIgnoreCaseOrCustomerEmailContainingIgnoreCaseOrCustomerPhoneNoContaining(
                        keyword,keyword,keyword);
    }

    //5.ADD CUSTOMER
    public Customer addCustomer(Customer customer){
        log.info("Adding Customers With Name:{} and Email:{}",customer.getCustomerName(),customer.getCustomerEmail());
        Customer saved=customerRepository.save(customer);
        log.info("Added Customer With Id:{}",saved.getCustomerId());
        return saved;
    }

    //6.DELETE CUSTOMER BY CUSTOMER ID
    public void deleteCustomer(Integer customerId){
        log.info("Attempting to delete customer with id: {}", customerId);

        if (!customerRepository.existsById(customerId)) {
            log.warn("Customer With Id: {} Not Found", customerId);
            throw new CustomerNotFoundException(customerId);
        }
        customerRepository.deleteById(customerId);
        log.info("Customer With Id:{} is Deleted", customerId);
    }

    //7.UPDATE CUSTOMER DETAILS BY CUSTOMER ID
    public Customer updateCustomer(Integer customerId,Customer updated){
        log.info("Updating Customer with id: {}", customerId);
        Customer existing=getCustomerById(customerId);

        if(updated.getCustomerName()!=null) {
            existing.setCustomerName(updated.getCustomerName());
        }
        if(updated.getCustomerPhoneNo()!=null) {
            existing.setCustomerPhoneNo(updated.getCustomerPhoneNo());
        }
        if(updated.getCustomerEmail()!=null) {
            existing.setCustomerEmail(updated.getCustomerEmail());
        }
        Customer saved=customerRepository.save(existing);
        log.info("Customer with id:{} updated successfully", saved.getCustomerId());
        return saved;
    }
}
