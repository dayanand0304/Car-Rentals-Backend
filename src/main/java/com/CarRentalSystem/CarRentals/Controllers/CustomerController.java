package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.Request.CustomerCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.DTO.Response.PageResponse;
import com.CarRentalSystem.CarRentals.Enums.Role;
import com.CarRentalSystem.CarRentals.Services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;

    //1.GET ALL CUSTOMERS
    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> getCustomers(
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String customerPhoneNo,
            @RequestParam(required = false) Role role,
            @PageableDefault(size = 5,sort = "customerId")Pageable pageable
            ){

        PageResponse<CustomerResponse> customers;

        if(customerName!=null){
            customers = customerService.getCustomersByName(customerName,pageable);

        }else if(customerPhoneNo!=null){
            customers = customerService.getCustomersByPhoneNoContaining(customerPhoneNo,pageable);

        }else if(role!=null){
            customers = customerService.getCustomersByRole(role,pageable);

        }else{
            customers = customerService.getAllCustomers(pageable);
        }

        return ResponseEntity.ok(customers);
    }

    //2.GET CUSTOMER BY CUSTOMER ID
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer customerId){
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    //GET CUSTOMER BY PHONE NO
    @GetMapping("/phone")
    public ResponseEntity<CustomerResponse> getCustomerByPhoneNo(@RequestParam String number){
        return ResponseEntity.ok(customerService.getCustomerByPhoneNo(number));
    }

    //GET CUSTOMER BY EMAIL
    @GetMapping("/email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@RequestParam String mail){
        return ResponseEntity.ok(customerService.getCustomerByEmail(mail));
    }

    //5.ADD CUSTOMER
    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.addCustomer(request));
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
                                                           @RequestBody CustomerUpdateRequest request){

        return ResponseEntity.ok(customerService.updateCustomer(customerId,request));
    }
}
