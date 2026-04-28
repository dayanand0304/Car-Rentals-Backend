package com.CarRentalSystem.CarRentals.DTO.Request;

import com.CarRentalSystem.CarRentals.Enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for registering a new customer")
public class RegisterRequest {

    @Schema(
            description = "Full name of the customer",
            example = "Dayanand"
    )
    @NotBlank(message = "Customer must not be blank")
    @Size(max = 100, message = "customer name length must not exceed")
    private String customerName;

    @Schema(
            description = "10-digit Indian mobile number",
            example = "9876543210"
    )
    @NotBlank(message = "phone number must not be blank")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Phone Number"
    )
    private String customerPhoneNo;

    @Schema(
            description = "Customer email address",
            example = "dayanand03@example.com"
    )
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid Email Format")
    private String customerEmail;

    @Schema(
            description = "Password (8-20 chars, must include uppercase, lowercase, number, special char)",
            example = "Strong@123",
            format = "password"
    )
    @NotBlank(message = "password must not be blank")
    @Size(min = 8, max = 20, message = "password must in 8-20 characters")
    @Pattern(regexp = ".*[A-Z].*", message = "password must contain at least one Upper case character")
    @Pattern(regexp = ".*[a-z].*", message = "password must contain at least one Lower case character")
    @Pattern(regexp = ".*\\d.*", message = "password must contain at least one number")
    @Pattern(regexp = ".*[!@$%^&*].*", message = "password must contain at least one Special character")
    private String password;

    @Schema(
            description = "Role of the user",
            example = "CUSTOMER",
            defaultValue = "CUSTOMER"
    )
    private Role role = Role.CUSTOMER;
}