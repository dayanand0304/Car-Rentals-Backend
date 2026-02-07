package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    @NotBlank(message = "Customer must not be blank")
    @Size(max = 100,message = "customer name length must not exceed")
    private String customerName;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Phone Number"
    )
    private String customerPhoneNo;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid Email Format")
    private String customerEmail;

    @NotBlank(message = "password must not be blank")
    @Size(min = 8,max=20, message = "password must in 8-20 characters")
    private String password;

    @NotNull(message = "role must not be null")
    private Role role;
}
