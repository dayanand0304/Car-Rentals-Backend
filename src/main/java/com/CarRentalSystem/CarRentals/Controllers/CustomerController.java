package com.CarRentalSystem.CarRentals.Controllers;

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
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers=customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    //2.GET CUSTOMER BY CUSTOMER ID
    @GetMapping("/id/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId){
        Customer customer=customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    //3.GET CUSTOMER BY CUSTOMER NAME
    @GetMapping("/name/{customerName}")
    public ResponseEntity<List<Customer>> getByCustomerName(@PathVariable String customerName){
        List<Customer> customers=customerService.getCustomerByName(customerName);
        return ResponseEntity.ok(customers);
    }

    //4.GET CUSTOMERS BY PARTIAL CONTAINING AND CASE-INSENSITIVE SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> getCustomerByNameContaining(
            @RequestParam String keyword){
        List<Customer> customers=customerService.searchCustomers(keyword);
        return ResponseEntity.ok(customers);
    }

    //5.ADD CUSTOMER
    @PostMapping("/add-customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        Customer newCustomer=customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    //6.DELETE CUSTOMER BY CUSTOMER ID
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Integer customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    //7.UPDATE CUSTOMER DETAILS BY CUSTOMER ID
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer customerId,
                                                   @RequestBody Customer updated){
        Customer customer=customerService.updateCustomer(customerId,updated);
        return ResponseEntity.ok(customer);
    }
}
