package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.Request.RegisterRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerUpdateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.DTO.Response.PageResponse;
import com.CarRentalSystem.CarRentals.Enums.Role;
import com.CarRentalSystem.CarRentals.Services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer APIs", description = "Operations related to customer management")
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasRole('ADMIN')")
public class CustomerController {

    private final CustomerService customerService;

    // GET ALL CUSTOMERS
    @Operation(
            summary = "Get Customers",
            description = "Fetch customers with optional filters like name, phone number, or role"
    )
    @ApiResponse(responseCode = "200", description = "Customers fetched successfully",
            content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> getCustomers(

            @RequestParam(required = false)
            @Size(max = 100, message = "customer name length must not exceed")
            String customerName,

            @RequestParam(required = false)
            @Pattern(
                    regexp = "^[6-9]\\d{9}$",
                    message = "Invalid Phone Number"
            )
            String customerPhoneNo,

            @RequestParam(required = false) Role role,

            @ParameterObject @PageableDefault(size = 5, sort = "customerId") Pageable pageable
    ) {

        PageResponse<CustomerResponse> customers;

        if (customerName != null) {
            customers = customerService.getCustomersByName(customerName, pageable);

        } else if (customerPhoneNo != null) {
            customers = customerService.getCustomersByPhoneNoContaining(customerPhoneNo, pageable);

        } else if (role != null) {
            customers = customerService.getCustomersByRole(role, pageable);

        } else {
            customers = customerService.getAllCustomers(pageable);
        }

        return ResponseEntity.ok(customers);
    }

    // GET CUSTOMER BY ID
    @Operation(summary = "Get Customer by ID",
            description = "Fetch a customer using their unique ID")
    @ApiResponse(responseCode = "200", description = "Customer found",
            content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    // GET CUSTOMER BY PHONE
    @Operation(summary = "Get Customer by Phone Number",
            description = "Fetch a customer using their phone number")
    @ApiResponse(responseCode = "200", description = "Customer found",
            content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    @GetMapping("/phone")
    public ResponseEntity<CustomerResponse> getCustomerByPhoneNo(
            @RequestParam
            @NotBlank(message = "phone number must not be blank")
            @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Phone Number")
            String number) {

        return ResponseEntity.ok(customerService.getCustomerByPhoneNo(number));
    }

    // GET CUSTOMER BY EMAIL
    @Operation(summary = "Get Customer by Email",
            description = "Fetch a customer using their email address")
    @ApiResponse(responseCode = "200", description = "Customer found",
            content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    @GetMapping("/email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(
            @RequestParam
            @NotBlank(message = "Email must not be blank")
            @Email(message = "Invalid Email Format")
            String mail) {

        return ResponseEntity.ok(customerService.getCustomerByEmail(mail));
    }

    // DEACTIVATE CUSTOMER
    @Operation(summary = "Deactivate Customer",
            description = "Deactivate a customer account by ID")
    @ApiResponse(responseCode = "204", description = "Customer deactivated successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @DeleteMapping("/{customerId}/de-activate")
    public ResponseEntity<String> deActivateCustomer(@PathVariable Integer customerId) {
        customerService.deActivateCustomer(customerId);
        return ResponseEntity.ok("Customer with Id: "+customerId+" DeActivated Successfully");
    }

    // REACTIVATE CUSTOMER
    @Operation(summary = "Reactivate Customer",
            description = "Reactivate a customer account by ID")
    @ApiResponse(responseCode = "204", description = "Customer reactivated successfully")
    @PutMapping("/{customerId}/re-activate")
    public ResponseEntity<String> reActivateCustomer(@PathVariable Integer customerId) {
        customerService.reActivateCustomer(customerId);
        return ResponseEntity.ok("Customer with Id: "+customerId+" ReActivated Successfully");
    }

    // UPDATE CUSTOMER
    @Operation(summary = "Update Customer",
            description = "Update customer details partially using customer ID")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully",
            content = @Content(schema = @Schema(implementation = CustomerResponse.class)))
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Integer customerId,
            @Valid @RequestBody CustomerUpdateRequest request) {

        return ResponseEntity.ok(customerService.updateCustomer(customerId, request));
    }
}