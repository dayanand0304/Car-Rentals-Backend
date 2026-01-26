package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.CustomerMapper;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;

    //1.GET ALL CUSTOMERS
    @GetMapping("/get-all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        List<CustomerResponse> customers=customerService.getAllCustomers()
                .stream()
                .map(CustomerMapper::response)
                .toList();
        return ResponseEntity.ok(customers);
    }

    //2.GET CUSTOMER BY CUSTOMER ID
    @GetMapping("/id/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer customerId){
        Customer customer=customerService.getCustomerById(customerId);
        return ResponseEntity.ok(CustomerMapper.response(customer));
    }

    //3.GET CUSTOMER BY CUSTOMER NAME
    @GetMapping("/name/{customerName}")
    public ResponseEntity<List<CustomerResponse>> getByCustomerName(@PathVariable String customerName){
        List<CustomerResponse> customers=customerService.getCustomerByName(customerName)
                .stream()
                .map(CustomerMapper::response)
                .toList();
        return ResponseEntity.ok(customers);
    }

    //4.GET CUSTOMERS BY PARTIAL CONTAINING AND CASE-INSENSITIVE SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> getCustomerByNameContaining(
            @RequestParam String keyword){
        List<CustomerResponse> customers=customerService.searchCustomers(keyword)
                .stream()
                .map(CustomerMapper::response)
                .toList();
        return ResponseEntity.ok(customers);
    }

    //5.ADD CUSTOMER
    @PostMapping("/add-customer")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerCreateRequest customer){
        Customer newCustomer=CustomerMapper.create(customer);
        Customer saved=customerService.addCustomer(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.response(saved));
    }

    //6.DELETE CUSTOMER BY CUSTOMER ID
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Integer customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    //7.UPDATE CUSTOMER DETAILS BY CUSTOMER ID
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Integer customerId,
                                                   @RequestBody CustomerCreateRequest customer){

        Customer update=CustomerMapper.create(customer);
        Customer updated=customerService.updateCustomer(customerId,update);
        return ResponseEntity.ok(CustomerMapper.response(updated));
    }
}
