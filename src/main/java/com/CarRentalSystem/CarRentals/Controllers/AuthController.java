package com.CarRentalSystem.CarRentals.Controllers;

import com.CarRentalSystem.CarRentals.DTO.Request.LoginRequest;
import com.CarRentalSystem.CarRentals.DTO.Request.RegisterRequest;
import com.CarRentalSystem.CarRentals.DTO.Response.CustomerResponse;
import com.CarRentalSystem.CarRentals.Entities.Customer;
import com.CarRentalSystem.CarRentals.Entities.RefreshToken;
import com.CarRentalSystem.CarRentals.Security.JwtService;
import com.CarRentalSystem.CarRentals.Services.CustomerService;
import com.CarRentalSystem.CarRentals.Services.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Ref;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCustomerEmail(),
                        request.getPassword()
                )
        );
        Customer customer=(Customer) auth.getPrincipal();

        String accessToken=jwtService.generateToken(customer.getCustomerEmail());

        RefreshToken refreshToken=refreshTokenService.createRefreshToken(customer);

        return ResponseEntity.ok("AccessToken: "+ jwtService.generateToken(request.getCustomerEmail())
                + "\n"+ "RefreshToken: "+ refreshToken.getToken());
    }

    //REGISTER
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.addCustomer(request));
    }
}
