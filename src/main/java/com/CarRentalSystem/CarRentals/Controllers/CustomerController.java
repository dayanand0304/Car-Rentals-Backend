package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.Request.RegisterRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.DTO.Response.PageResponse;
import com.CarRentalSystem.CarRentals.Enums.Role;
import com.CarRentalSystem.CarRentals.Services.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasRole('ADMIN')")
public class CustomerController {


    private final CustomerService customerService;

    //1.GET ALL CUSTOMERS
    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> getCustomers(
            @RequestParam(required = false)
            @Size(max = 100,message = "customer name length must not exceed")
            String customerName,

            @RequestParam(required = false)
            @Pattern(
                    regexp = "^[6-9]\\d{9}$",
                    message = "Invalid Phone Number"
            )
            String customerPhoneNo,
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
    public ResponseEntity<CustomerResponse> getCustomerByPhoneNo(@RequestParam
                                                                     @NotBlank(message = "phone number must not be blank")
                                                                     @Pattern(
                                                                             regexp = "^[6-9]\\d{9}$", message = "Invalid Phone Number"
                                                                     )
                                                                     String number){
        return ResponseEntity.ok(customerService.getCustomerByPhoneNo(number));
    }

    //GET CUSTOMER BY EMAIL
    @GetMapping("/email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@RequestParam
                                                                   @NotBlank(message = "Email must not be blank")
                                                                   @Email(message = "Invalid Email Format")
                                                                   String mail){
        return ResponseEntity.ok(customerService.getCustomerByEmail(mail));
    }

    //5.ADD CUSTOMER
    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.addCustomer(request));
    }

    //6.DE-ACTIVATE CUSTOMER
    @DeleteMapping("/{customerId}/de-activate")
    public ResponseEntity<Void> deActivateCustomer(@PathVariable Integer customerId){
        customerService.deActivateCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    //RE-ACTIVATE CUSTOMER
    @PutMapping("/{customerId}/re-activate")
    public ResponseEntity<Void> reActivateCustomer(@PathVariable Integer customerId){
        customerService.reActivateCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    //7.UPDATE CUSTOMER DETAILS BY CUSTOMER ID
    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Integer customerId,
                                                           @Valid @RequestBody CustomerUpdateRequest request){

        return ResponseEntity.ok(customerService.updateCustomer(customerId,request));
    }
}
