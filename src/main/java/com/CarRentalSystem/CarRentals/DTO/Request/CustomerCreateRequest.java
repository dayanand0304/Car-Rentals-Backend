package com.CarRentalSystem.CarRentals.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    @NotBlank(message = "Customer must not be blank")
    private String customerName;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Phone Number"
    )
    private String customerPhoneNo;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid Email Format")
    private String customerEmail;
}
