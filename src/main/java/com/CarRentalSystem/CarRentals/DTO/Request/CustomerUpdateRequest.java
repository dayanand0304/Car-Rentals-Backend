package com.CarRentalSystem.CarRentals.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for updating customer details (all fields are optional)")
public class CustomerUpdateRequest {

    @Schema(
            description = "Updated name of the customer",
            example = "Dayanand"
    )
    @Size(max = 100, message = "customer name length must not exceed 100")
    private String customerName;

    @Schema(
            description = "Updated phone number (10-digit Indian mobile number)",
            example = "9876543210"
    )
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Phone Number"
    )
    private String customerPhoneNo;

    @Schema(
            description = "Updated email address",
            example = "dayanand03@example.com"
    )
    @Email(message = "Invalid Email Format")
    private String customerEmail;

    @Schema(
            description = "Updated password (8-20 characters)",
            example = "StrongPass123",
            format = "password"
    )
    @Size(min = 8, max = 20, message = "password must in 8-20 characters")
    private String password;
}