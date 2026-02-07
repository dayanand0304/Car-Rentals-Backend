package com.CarRentalSystem.CarRentals.DTO.Request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateRequest {

    @Size(max = 100,message = "customer name length must not exceed 100")
    private String customerName;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Phone Number"
    )
    private String customerPhoneNo;

    @Email(message = "Invalid Email Format")
    private String customerEmail;

    @Size(min = 8,max=20, message = "password must in 8-20 characters")
    private String password;
}
