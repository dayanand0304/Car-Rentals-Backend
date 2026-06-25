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
    @Pattern(regexp = ".*[A-Z].*", message = "password must contain at least one Upper case character")
    @Pattern(regexp = ".*[a-z].*", message = "password must contain at least one Lower case character")
    @Pattern(regexp = ".*\\d.*", message = "password must contain at least one number")
    @Pattern(regexp = ".*[!@$%^&*].*", message = "password must contain at least one Special character")
    private String password;
}
