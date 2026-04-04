package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.Request.AuthRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.CustomerCreateRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.Security.JwtService;
import com.CarRentalSystem.CarRentals.Services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCustomerEmail(),
                        request.getPassword()
                )
        );
        return jwtService.generateToken(request.getCustomerEmail());
    }

    //5.ADD CUSTOMER
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.addCustomer(request));
    }
}
