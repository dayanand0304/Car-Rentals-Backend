package com.CarRentalSystem.CarRentals.Services;

import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerAlreadyDeletedException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerAlreadyExistsException;
import com.CarRentalSystem.CarRentals.CustomExceptions.Customers.CustomerNotFoundException;
import com.CarRentalSystem.CarRentals.DTO.CustomerMapper;
import com.CarRentalSystem.CarRentals.DTO.PageMapper;
import com.CarRentalSystem.CarRentals.DTO.Request.RegisterRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.DTO.Response.PageResponse;
import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Enums.Role;
import com.CarRentalSystem.CarRentals.Repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;


    //1.GET ALL CUSTOMERS
    public PageResponse<CustomerResponse> getAllCustomers(Pageable pageable){
        log.info("Fetching All Customers");
        Page<Customer> page=customerRepository.findAll(pageable);

        return PageMapper.toPageResponse(page,CustomerMapper::response);
    }



    //2.GET CUSTOMER BY CUSTOMER ID
    public CustomerResponse getCustomerById(Integer customerId){
        log.info("Fetching All Customers By Id:{}",customerId);
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException(customerId));

        return CustomerMapper.response(customer);
    }

    //3.GET CUSTOMERS BY CUSTOMER NAME CONTAINING
    public PageResponse<CustomerResponse> getCustomersByName(String customerName,Pageable pageable){
        log.info("Fetching All Customers By Customer Name:{}",customerName);
        Page<Customer> page=customerRepository.findByCustomerNameContainingIgnoreCase(customerName,pageable);
        return PageMapper.toPageResponse(page,CustomerMapper::response);
    }

    //GET CUSTOMER BY EMAIL
    public CustomerResponse getCustomerByEmail(String mail){
        Customer customer=customerRepository.findByCustomerEmailIgnoreCase(mail)
                .orElseThrow(()->new CustomerNotFoundException("Customer with email: "+mail+" not found"));

        return CustomerMapper.response(customer);
    }

    //GET CUSTOMER ENTITY BY EMAIL
    public Customer getCustomerEntityByEmail(String email) {
        return customerRepository.findByCustomerEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    //GET CUSTOMERS BY ROLE
    public PageResponse<CustomerResponse> getCustomersByRole(Role role,Pageable pageable){
        Page<Customer> page=customerRepository.findByRole(role,pageable);

        return PageMapper.toPageResponse(page,CustomerMapper::response);
    }

    //GET CUSTOMERS BY ACTIVE
    public PageResponse<CustomerResponse> getCustomersByActive(boolean active,Pageable pageable){
        Page<Customer> page=customerRepository.findByActive(active,pageable);

        return PageMapper.toPageResponse(page,CustomerMapper::response);
    }

    //5.ADD CUSTOMER
    public CustomerResponse addCustomer(RegisterRequest request){

        Customer customer=CustomerMapper.create(request);

        log.info("Adding Customers With Name:{} and Email:{}",customer.getCustomerName(),customer.getCustomerEmail());

        if(customerRepository.existsByCustomerEmail(customer.getCustomerEmail())){
            throw new CustomerAlreadyExistsException();
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        if(request.getRole()==null){
            customer.setRole(Role.CUSTOMER);
        }else{
            customer.setRole(request.getRole());
        }
        Customer saved=customerRepository.save(customer);
        log.info("Added Customer With Id:{}",saved.getCustomerId());

        return CustomerMapper.response(saved);
    }

    //6.DE-ACTIVATE CUSTOMER
    @Transactional
    public void deActivateCustomer(Integer customerId){
        log.info("Attempting to delete customer with id: {}", customerId);
        Customer customer=customerRepository.findById(customerId)
                        .orElseThrow(()->new CustomerNotFoundException(customerId));

        if(!customer.isActive()){
            throw new CustomerAlreadyDeletedException(customerId);
        }
        customer.setActive(false);
        customer.setDeletedAt(LocalDateTime.now());

        customerRepository.save(customer);
    }

    //RE-ACTIVATE CUSTOMER
    public void reActivateCustomer(Integer customerId){
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException(customerId));

        customer.setActive(true);
        customer.setDeletedAt(null);
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
