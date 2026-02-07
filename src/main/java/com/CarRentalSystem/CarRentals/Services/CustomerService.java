package com.CarRentalSystem.CarRentals.Services;

import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerAlreadyExistsException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerNotFoundException;
import com.CarRentalSystem.CarRentals.DTO.CustomerMapper;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Enums.Role;
import com.CarRentalSystem.CarRentals.Repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    //1.GET ALL CUSTOMERS
    public List<CustomerResponse> getAllCustomers(){
        log.info("Fetching All Customers");
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::response)
                .toList();
    }



    //2.GET CUSTOMER BY CUSTOMER ID
    public CustomerResponse getCustomerById(Integer customerId){
        log.info("Fetching All Customers By Id:{}",customerId);
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException(customerId));

        return CustomerMapper.response(customer);
    }

    //3.GET CUSTOMERS BY CUSTOMER NAME CONTAINING
    public List<CustomerResponse> getCustomersByName(String customerName){
        log.info("Fetching All Customers By Customer Name:{}",customerName);
        return customerRepository.findByCustomerNameContainingIgnoreCase(customerName)
                .stream()
                .map(CustomerMapper::response)
                .toList();
    }

    //GET CUSTOMER BY CUSTOMER PHONE NO
    public CustomerResponse getCustomerByPhoneNo(String number){
        Customer customer=customerRepository.findByCustomerPhoneNo(number)
                .orElseThrow(()->new CustomerNotFoundException("Customer with Phone No: "+number+" not found"));

        return CustomerMapper.response(customer);
    }

    //GET CUSTOMERS BY CUSTOMER PHONE NO CONTAINING
    public List<CustomerResponse> getCustomersByPhoneNoContaining(String number){
        return customerRepository.findByCustomerPhoneNoContaining(number)
                .stream()
                .map(CustomerMapper::response)
                .toList();
    }

    //GET CUSTOMER BY EMAIL
    public CustomerResponse getCustomerByEmail(String mail){
        Customer customer=customerRepository.findByCustomerEmailIgnoreCase(mail)
                .orElseThrow(()->new CustomerNotFoundException("Customer with email: "+mail+" not found"));

        return CustomerMapper.response(customer);
    }

    //GET CUSTOMERS BY ROLE
    public List<CustomerResponse> getCustomersByRole(Role role){
        return customerRepository.findByRole(role)
                .stream()
                .map(CustomerMapper::response)
                .toList();
    }

    //GET CUSTOMERS BY ACTIVE
    public List<CustomerResponse> getCustomersByActive(boolean active){
        return customerRepository.findByActive(active)
                .stream()
                .map(CustomerMapper::response)
                .toList();
    }

    //5.ADD CUSTOMER
    public CustomerResponse addCustomer(CustomerCreateRequest request){

        Customer customer=CustomerMapper.create(request);

        log.info("Adding Customers With Name:{} and Email:{}",customer.getCustomerName(),customer.getCustomerEmail());

        if(customerRepository.existsByCustomerEmail(customer.getCustomerEmail()) ||
                customerRepository.existsByCustomerPhoneNo(customer.getCustomerPhoneNo())){
            throw new CustomerAlreadyExistsException();
        }
        if(request.getRole()==null){
            customer.setRole(Role.CUSTOMER);
        }else{
            customer.setRole(request.getRole());
        }
        Customer saved=customerRepository.save(customer);
        log.info("Added Customer With Id:{}",saved.getCustomerId());

        return CustomerMapper.response(saved);
    }

    //6.DELETE CUSTOMER BY CUSTOMER ID
    @Transactional
    public void deleteCustomer(Integer customerId){
        log.info("Attempting to delete customer with id: {}", customerId);
        Customer customer=customerRepository.findById(customerId)
                        .orElseThrow(()->new CustomerNotFoundException(customerId));

        customer.setActive(false);
        customer.setDeletedAt(LocalDateTime.now());

        customerRepository.save(customer);
    }

    //7.UPDATE CUSTOMER DETAILS BY CUSTOMER ID
    @Transactional
    public CustomerResponse updateCustomer(Integer customerId, CustomerUpdateRequest request){
        log.info("Updating Customer with id: {}", customerId);
        return customerRepository.findById(customerId)
                .map(existing->{
                    if(!existing.isActive()){
                        throw new CustomerNotFoundException(customerId);
                    }
                    if(request.getCustomerName()!=null){
                        existing.setCustomerName(request.getCustomerName());
                    }
                    if(request.getCustomerPhoneNo()!=null){
                        if(customerRepository.existsByCustomerPhoneNo(request.getCustomerPhoneNo())){
                            throw new CustomerAlreadyExistsException();
                        }
                        existing.setCustomerPhoneNo(request.getCustomerPhoneNo());
                    }
                    if(request.getCustomerEmail()!=null){
                        if(customerRepository.existsByCustomerEmail(request.getCustomerEmail())){
                            throw new CustomerAlreadyExistsException();
                        }
                        existing.setCustomerEmail(request.getCustomerEmail());
                    }
                    if(request.getPassword()!=null){
                        existing.setPassword(request.getPassword());
                    }

                    Customer saved=customerRepository.save(existing);
                    return CustomerMapper.response(saved);
                })
                .orElseThrow(()->new CustomerNotFoundException(customerId));
    }
}
